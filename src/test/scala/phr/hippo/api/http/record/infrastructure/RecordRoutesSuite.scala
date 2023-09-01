package phr.hippo.api.http.record.infrastructure

import java.util.UUID
import scala.util.{ Failure, Success, Try }
import munit.*
import cats.Applicative
import cats.effect.IO
import cats.syntax.all.*
import io.circe.*
import io.circe.syntax.*
import io.circe.parser.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.io.*
import org.http4s.dsl.*
import org.http4s.implicits.*
import phr.hippo.api.http.record.application.RecordService
import phr.hippo.api.http.record.domain.*
import phr.hippo.api.http.record.infrastructure.formats.RecordJsonFormat.given

class RecordRoutesSuite extends munit.Http4sHttpRoutesSuite:
  val dummyRecord: Record = Record.dummy

  class RecordRepositoryMock[F[_]: Applicative] extends RecordRepository[F]:
    override def create(record: Record): F[Int] = 1.pure
    override def get(id: UUID): F[Option[Record]] = dummyRecord.some.pure
    override def list(patientId: UUID): F[List[Record]] = ???
    override def delete(id: UUID): F[Int] = 1.pure

  val recordRepository: RecordRepositoryMock[IO] = RecordRepositoryMock[IO]()

  override val routes: HttpRoutes[IO] =
    RecordRoutes[IO](RecordService(recordRepository)).allEndpoints

  test(GET(uri"record" / dummyRecord.header.id)).alias("Retrieve Record") { response =>
    val result = response.as[String].map(parse)
    val expected = dummyRecord.asJson

    assertIO(result, Right(expected))
  }

  test(POST(uri"record").withEntity[IO, Record](dummyRecord)).alias("Create Record has an id"):
    response =>
      val hasIdAndIsUUID =
        for
          json <- response.as[Json]
          decodeResult <- IO.pure(
            json.hcursor.get[Json]("header").flatMap(_.hcursor.get[String]("id"))
          )
          maybeIdAndMaybeUUID <- IO.pure(decodeResult.flatMap: maybeUUID =>
            Try(UUID.fromString(maybeUUID)).toEither)
        yield maybeIdAndMaybeUUID match
          case Right(_) => true
          case _ => false

      val expected = true

      assertIO(hasIdAndIsUUID, expected, "does not have id field or is not an UUID")
