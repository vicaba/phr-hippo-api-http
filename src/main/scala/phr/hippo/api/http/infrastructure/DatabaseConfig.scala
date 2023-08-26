package phr.hippo.api.http.infrastructure

import cats.effect.*
import cats.syntax.functor.*
import doobie.hikari.HikariTransactor

import scala.concurrent.ExecutionContext
case class DatabaseConfig(
  url: String,
  driver: String,
  user: String,
  password: String,
)

object DatabaseConfig:
  def transactor[F[_]: Async](
    config: DatabaseConfig,
    fixedThreadPool: ExecutionContext,
  ): Resource[F, HikariTransactor[F]] =
    HikariTransactor.newHikariTransactor[F](
      config.driver,
      config.url,
      config.user,
      config.password,
      fixedThreadPool,
    )
