package phr.hippo.api.http.record.application

import java.util.UUID

import cats.Applicative

import phr.hippo.api.http.record.domain.*

class RecordService[F[_]: Applicative](recordRepository: RecordRepository[F]):
  def create(record: Record): F[Int] = recordRepository.create(record)
  def get(id: UUID): F[Option[Record]] = recordRepository.get(id)
  def list(patientId: UUID): F[List[Record]] = recordRepository.list(patientId)
