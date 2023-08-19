package phr.hippo.api.http.record.domain

import java.util.UUID

import cats.Monad

trait RecordRepository[F[_]: Monad]:
  def create(record: Record): F[Record]
  def get(id: UUID): F[Option[Record]]
  def list(patientId: UUID): F[List[Record]]
