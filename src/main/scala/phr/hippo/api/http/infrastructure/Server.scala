package phr.hippo.api.http.infrastructure

import cats.effect.*
import cats.implicits.*
import com.comcast.ip4s.*
import fs2.io.net.Network
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server as Http4Server
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.*
import phr.hippo.api.http.record.application.RecordService
import phr.hippo.api.http.record.infrastructure.RecordRoutes
import phr.hippo.api.http.record.infrastructure.repository.DoobieRecordRepository

import scala.concurrent.ExecutionContext

object Server extends IOApp:
  def createServer[F[_]: Async: Network]: Resource[F, Http4Server] =
    // TODO: This parameters have to be extracted into a config file!
    val dbConfig = DatabaseConfig(
      "jdbc:postgresql:hippodb?connectionTimeZone=UTC",
      "org.postgresql.ds.PGSimpleDataSource",
      "docker",
      "docker",
    )

    for
      xa <- DatabaseConfig.transactor(dbConfig, ExecutionContext.global)
      recordService = RecordService[F](DoobieRecordRepository(xa))
      recordEndpoints = RecordRoutes[F](recordService).allEndpoints
      healthCheckEndpoint = HealthCheckRoutes[F]().allEndpoints
      allEndpoints = recordEndpoints <+> healthCheckEndpoint
      server <- EmberServerBuilder
        .default[F]
        .withHost(ipv4"0.0.0.0")
        .withPort(port"8081")
        .withHttpApp(allEndpoints.orNotFound)
        .build
    yield server

  override def run(args: List[String]): IO[ExitCode] =
    createServer[IO]
      .use(_ => IO.never)
      .as(ExitCode.Success)
