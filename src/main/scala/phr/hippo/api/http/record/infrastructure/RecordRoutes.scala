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
import phr.hippo.api.http.record.domain.Record
import phr.hippo.api.http.record.infrastructure.formats.RecordJsonFormat.given

class RecordRoutes[F[_]: Concurrent](recordService: RecordService[F]) extends Http4sDsl[F]:
  def createRecordEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F]:
      case req @ POST -> Root / "record" =>
        for
          record <- req.as[Record]
          saved <- recordService.create(record)
          resp <- Ok(saved.asJson)
        yield resp

  // /record/a2d67669-8ec1-4b10-832b-48afae445604
  def getRecordEndpoint: HttpRoutes[F] =
    HttpRoutes.of[F]:
      case GET -> Root / "record" / UUIDVar(recordId) =>
        recordService.get(recordId).flatMap { recordOpt =>
          recordOpt match
            case Some(record) => Ok(record.asJson)
            case None => NotFound("{ error: Record not found }")
        }

  def allEndpoints: HttpRoutes[F] =
    getRecordEndpoint

  def allEndpointsComplete: HttpApp[F] =
    allEndpoints.orNotFound
