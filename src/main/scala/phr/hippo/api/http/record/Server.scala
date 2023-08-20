package phr.hippo.api.http.record

import cats.effect.*

import com.comcast.ip4s.*
import org.http4s.ember.server.EmberServerBuilder
import org.typelevel.log4cats.*
import org.typelevel.log4cats.slf4j.*

import phr.hippo.api.http.record.application.RecordService
import phr.hippo.api.http.record.infrastructure.RecordRoutes
import phr.hippo.api.http.record.infrastructure.repository.DummyRecordRepository

object Server extends IOApp:
  override def run(args: List[String]): IO[ExitCode] =
    val recordService = RecordService[IO](DummyRecordRepository())
    val endpoints = RecordRoutes[IO](recordService).allEndpointsComplete

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8081")
      .withHttpApp(endpoints)
      .build
      .use(_ => IO.never)
      .as(ExitCode.Success)
