package phr.hippo.api.http.record.application

import cats.Monad
import cats.syntax.all.*
import java.util.UUID
import phr.hippo.api.http.record.domain.*
import phr.hippo.api.http.record.infrastructure.formats.Messages.CreateRecord

class RecordService[F[_]: Monad](recordRepository: RecordRepository[F]):
  def create(cr: CreateRecord): F[Option[Record]] =
    val record = cr.toRecord
    recordRepository
      .create(record)
      .map:
        case 1 => Some(record)
        case _ => None

  def get(id: UUID): F[Option[Record]] = recordRepository.get(id)

  def list(patientId: UUID): F[List[Record]] = recordRepository.list(patientId)

  def delete(id: UUID): F[Int] = recordRepository.delete(id)
