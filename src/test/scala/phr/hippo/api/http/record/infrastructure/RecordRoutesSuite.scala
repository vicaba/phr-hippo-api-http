package phr.hippo.api.http.record.infrastructure

import java.util.UUID

import cats.Applicative
import cats.effect.*
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

class RecordRoutesSuite extends munit.Http4sHttpRoutesSuite:
  class RecordRepositoryMock[F[_]: Applicative] extends RecordRepository[F]:
    val dummyRecord: Record = Record.dummy
    def create(record: Record): F[Int] = 1.pure
    def get(id: UUID): F[Option[Record]] = dummyRecord.some.pure
    def list(patientId: UUID): F[List[Record]] = ???

  val recordRepository: RecordRepositoryMock[IO] = RecordRepositoryMock[IO]()

  override val routes: HttpRoutes[IO] =
    RecordRoutes[IO](RecordService(recordRepository)).allEndpoints

  test(GET(uri"record" / UUID.randomUUID())).alias("Say hello to Jose") { response =>
    val result = response.as[String].map(parse)
    val expected = Record.dummy.asJson

    // TODO: Not working
    assertIO(result, Right(expected))

    /*
    val json: String = """
      {
        "id": "c730433b-082c-4984-9d66-855c243266f0",
        "name": "Foo",
        "counts": [1, 2, 3],
        "values": {
          "bar": true,
          "baz": 100.001,
          "qux": ["a", "b"]
        }
      }
    """

    val doc: Json = parse(json).getOrElse(Json.Null)

    assertEquals(doc, doc)

     */
  }

  // test("RecordRoutes.getRecordEndpoint"):
  //   val response: OptionT[cats.effect.IO, Response[cats.effect.IO]] =
  //     RecordRoutes[IO](RecordService(recordRepository))
  //       .allEndpoints
  //       .run(Request(method = Method.GET, uri = uri"/user/not-used"))
