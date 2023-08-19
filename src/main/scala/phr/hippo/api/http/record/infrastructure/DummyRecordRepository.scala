package phr.hippo.api.http.record.infrastructure

import java.util.UUID

import cats.Monad
import cats.syntax.all.*

import phr.hippo.api.http.record.domain.*

class DummyRecordRepository[F[_]: Monad] extends RecordRepository[F]:
  def create(record: Record): F[Record] = Record.dummy.pure[F]
  def get(id: UUID): F[Option[Record]] = Record.dummy.some.pure[F]
  def list(patientId: UUID): F[List[Record]] = (Record.dummy :: Record.dummy :: Nil).pure[F]
