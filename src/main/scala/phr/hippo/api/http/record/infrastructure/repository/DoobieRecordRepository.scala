package phr.hippo.api.http.record.infrastructure.repository

import java.util.UUID

import cats.*
import cats.syntax.all.*
import cats.effect.Sync

import doobie.implicits.*
import doobie.util.query.Query0
import doobie.util.transactor.Transactor
import doobie.util.update.Update0
import doobie.postgres.*
import doobie.postgres.implicits.*

import phr.hippo.api.http.record.domain.*

object RecordSQL:
  def insertSQL(r: Record): Update0 =
    sql"""|
          |INSERT INTO record
          |(id, created_at, updated_at, patient_id, headline, body)
          |VALUES
          |(${r.header.id}, ${r.header.createdAt}, ${r.header.updatedAt},
          |${r.header.patientId}, ${r.header.headline}, ${r.body.body})
       """.stripMargin.update

  def getSQL(id: UUID): Query0[Record] =
    sql"""
        |SELECT id, created_at, updated_at, patient_id, headline, body
        |FROM record
        |WHERE id = $id
      """.stripMargin.query

  def deleteSQL(id: UUID): Update0 =
    sql"""
         |DELETE FROM record WHERE id = $id;
      """.stripMargin.update

class DoobieRecordRepository[F[_]: Sync](xa: Transactor[F]) extends RecordRepository[F]:
  import RecordSQL.*
  override def create(record: Record): F[Int] =
    insertSQL(record).run.transact(xa)
  override def get(id: UUID): F[Option[Record]] =
    getSQL(id).option.transact(xa)
  override def list(patientId: UUID): F[List[Record]] =
    (Record.dummy :: Record.dummy :: Nil).pure[F]
  override def delete(id: UUID): F[Int] = deleteSQL(id).run.transact(xa)
