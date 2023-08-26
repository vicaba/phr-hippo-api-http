package phr.hippo.api.http.record.infrastructure

import java.util.UUID
import cats.Applicative
import cats.effect.IO
import cats.syntax.all.*
import io.circe.*
import io.circe.syntax.*
import io.circe.parser.*
import munit.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.dsl.*
import org.http4s.implicits.*
import phr.hippo.api.http.record.application.RecordService
import phr.hippo.api.http.record.domain.*
import phr.hippo.api.http.record.infrastructure.formats.RecordJsonFormat.given

import scala.util.Try

class RecordRoutesSuite extends munit.Http4sHttpRoutesSuite:
  val dummyRecord: Record = Record.dummy

  class RecordRepositoryMock[F[_]: Applicative] extends RecordRepository[F]:
    def create(record: Record): F[Int] = 1.pure
    def get(id: UUID): F[Option[Record]] = dummyRecord.some.pure
    def list(patientId: UUID): F[List[Record]] = ???

  val recordRepository: RecordRepositoryMock[IO] = RecordRepositoryMock[IO]()

  override val routes: HttpRoutes[IO] =
    RecordRoutes[IO](RecordService(recordRepository)).allEndpoints

  test(GET(uri"record" / dummyRecord.header.id)).alias("Retrieve Record") { response =>
    val result = response.as[String].map(parse)
    val expected = dummyRecord.asJson

    assertIO(result, Right(expected))
  }

  test(POST(uri"record").withEntity[IO, Record](dummyRecord)).alias("Create Record") { response =>
    val result = response.as[String].map(n => Try(n.toInt).toEither)
    val expected = 1

    assertIO(result, Right(expected))
  }
