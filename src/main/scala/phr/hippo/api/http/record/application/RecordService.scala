package phr.hippo.api.http.record.application

import cats.Monad
import cats.syntax.all.*
import phr.hippo.api.http.record.application.Commands.CreateRecord

import java.util.UUID
import phr.hippo.api.http.record.domain.*

import java.time.Instant

object Commands:
  case class CreateRecordHeader(patientId: UUID, headline: String)
  case class CreateRecordBody(body: String)
  case class CreateRecord(header: CreateRecordHeader, body: CreateRecordBody):
    def toRecord: Record = Record(
      RecordHeader(
        UUID.randomUUID(),
        Instant.now(),
        Instant.now(),
        header.patientId,
        header.headline,
      ),
      RecordBody(body.body),
    )

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
