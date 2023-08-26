package phr.hippo.api.http.record.domain

import java.util.UUID

import cats.Monad

trait RecordRepository[F[_]]:
  def create(record: Record): F[Int]
  def get(id: UUID): F[Option[Record]]
  def list(patientId: UUID): F[List[Record]]
  def delete(id: UUID): F[Int]
