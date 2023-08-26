package phr.hippo.api.http.record

import cats.effect.*
import com.comcast.ip4s.*
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.*
import phr.hippo.api.http.record.application.RecordService
import phr.hippo.api.http.record.infrastructure.RecordRoutes
import phr.hippo.api.http.record.infrastructure.repository.DummyRecordRepository
import phr.hippo.api.http.record.infrastructure.repository.DoobieRecordRepository
import doobie.util.transactor.Transactor
import phr.hippo.api.http.infrastructure.DatabaseConfig

import org.http4s.server.Server as Http4Server

import scala.concurrent.ExecutionContext

object Server extends IOApp:
  def createServer[F[_]: Async]: Resource[F, Http4Server] =
    val dbConfig = DatabaseConfig(
      "jdbc:postgresql:hippodb",
      "org.postgresql.ds.PGSimpleDataSource",
      "docker",
      "docker",
    )

    for
      xa <- DatabaseConfig.transactor(dbConfig, ExecutionContext.global)
      recordService = RecordService[F](DoobieRecordRepository(xa))
      endpoints = RecordRoutes[F](recordService).allEndpointsComplete
      server <- EmberServerBuilder
        .default[F]
        .withHost(ipv4"0.0.0.0")
        .withPort(port"8081")
        .withHttpApp(endpoints)
        .build
    yield server

  override def run(args: List[String]): IO[ExitCode] =
    createServer[IO]
      .use(_ => IO.never)
      .as(ExitCode.Success)
