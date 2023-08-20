package phr.hippo.api.http.record.infrastructure.repository

import java.util.UUID

import cats.*
import cats.syntax.all.*

import doobie.implicits.*
import doobie.util.fragment.Fragment
import doobie.util.query.Query0
import doobie.util.transactor.Transactor
import doobie.util.update.Update0
import doobie.postgres.*
import doobie.postgres.implicits.*

import phr.hippo.api.http.record.domain.*
import phr.hippo.api.http.record.infrastructure.repository.DoobieRecordRepository.insert
import cats.effect.kernel.Sync
import cats.effect.IOApp
import cats.effect.ExitCode
import cats.effect.IO

object DoobieRecordRepository:
  def insert(r: Record): Update0 =
    sql"""|
          |INSERT INTO record
          |(id, created_at, updated_at, patient_id, headline, body)
          |VALUES
          |(${r.header.id}, ${r.header.createdAt}, ${r.header.updatedAt},
          |${r.header.patientId}, ${r.header.headline}, ${r.body.body})
       """.stripMargin.update

  // println(insert(Record.dummy))

class DoobieRecordRepository[F[_]: Sync](xa: Transactor[F]) extends RecordRepository[F]:
  def create(record: Record): F[Int] =
    insert(record).run.transact(xa)

  def get(id: UUID): F[Option[Record]] = Record.dummy.some.pure[F]
  def list(patientId: UUID): F[List[Record]] = (Record.dummy :: Record.dummy :: Nil).pure[F]

object Test extends IOApp:
  // TODO: Change to hikari execution context
  val xa: Transactor[IO] = Transactor.fromDriverManager[IO](
    driver = "org.postgresql.Driver",
    url = "jdbc:postgresql:hippodb", // Localhost by default
    user = "docker",
    password = "docker",
    logHandler = None,
  )
  def run(args: List[String]): IO[ExitCode] =
    DoobieRecordRepository[IO](xa).create(Record.dummy).map(_ => ExitCode.Success)
