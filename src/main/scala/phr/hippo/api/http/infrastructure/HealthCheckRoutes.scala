package phr.hippo.api.http.infrastructure

import cats.effect.Concurrent
import org.http4s.{ HttpApp, HttpRoutes }
import org.http4s.dsl.Http4sDsl

class HealthCheckRoutes[F[_]: Concurrent] extends Http4sDsl[F]:
  def healthCheckRoute: HttpRoutes[F] =
    HttpRoutes.of[F]:
      case GET -> Root / "server-status" / "health" => Ok()

  def allEndpoints: HttpRoutes[F] =
    healthCheckRoute

  def allEndpointsComplete: HttpApp[F] =
    allEndpoints.orNotFound
