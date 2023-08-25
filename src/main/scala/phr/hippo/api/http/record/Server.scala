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

object Server extends IOApp:

  override def run(args: List[String]): IO[ExitCode] =
    val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
      driver = "org.postgresql.Driver",
      url = "jdbc:postgresql:hippodb", // Localhost by default
      user = "docker",
      password = "docker",
      logHandler = None,
    )
    val recordService = RecordService[IO](DoobieRecordRepository(xa))
    val endpoints = RecordRoutes[IO](recordService).allEndpointsComplete

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8081")
      .withHttpApp(endpoints)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
