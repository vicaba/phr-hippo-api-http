package phr.hippo.api.http.record.infrastructure

import cats.*
import cats.effect.Concurrent
import cats.implicits.*
import io.circe.*
import io.circe.syntax.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.*
import org.http4s.implicits.*
import phr.hippo.api.http.record.application.*
import phr.hippo.api.http.record.application.Commands.*
import phr.hippo.api.http.record.domain.Record
import phr.hippo.api.http.record.infrastructure.formats.RecordJsonFormat.given

class RecordRoutes[F[_]: Concurrent](recordService: RecordService[F]) extends Http4sDsl[F]:
  def createRecordEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F]:
      case req @ POST -> Root / "record" =>
        req
          .attemptAs[CreateRecord]
          .value
          .flatMap:
            case Left(decodingFailure) =>
              UnprocessableEntity(decodingFailure.cause.get.toString)
            case Right(createRecord) =>
              for
                saved <- recordService.create(createRecord)
                resp <- saved match
                  case Some(record) => Ok(record)
                  case None =>
                    InternalServerError(
                      "{ error: The database could not process your request, try again later. }"
                    )
              yield resp

  // /record/a2d67669-8ec1-4b10-832b-48afae445604
  def getRecordEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F]:
      case GET -> Root / "record" / UUIDVar(recordId) =>
        recordService
          .get(recordId)
          .flatMap:
            case Some(record) => Ok(record.asJson)
            case None => NotFound("{ error: Record not found }")

  def deleteRecordEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F]:
      case DELETE -> Root / "record" / UUIDVar(recordId) =>
        recordService.delete(recordId) match
          case 1 => Ok()
          case _ => NotFound("{ error: Record not found }")

  def allEndpoints: HttpRoutes[F] =
    getRecordEndpoint <+> createRecordEndpoint <+> deleteRecordEndpoint

  def allEndpointsComplete: HttpApp[F] =
    allEndpoints.orNotFound
