package phr.hippo.api.http.record.infrastructure.formats

import cats.*
import cats.effect.Concurrent
import io.circe.*
import io.circe.generic.semiauto.*
import org.http4s.*
import org.http4s.circe.*
import phr.hippo.api.http.record.domain.{ Record, RecordBody, RecordHeader }

import java.time.Instant
import java.util.UUID

object Messages:
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

object MessagesJsonFormat:
  import Messages.*
  given createRecordDecoder: Decoder[CreateRecord] = deriveDecoder[CreateRecord]
  given createRecordEncoder: Encoder[CreateRecord] = deriveEncoder[CreateRecord]
  given createRecordEntityEncoder[F[_]: Monad]: EntityEncoder[F, CreateRecord] =
    jsonEncoderOf[CreateRecord]
  given createRecordEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, CreateRecord] =
    jsonOf[F, CreateRecord]

object RecordJsonFormat:
  given recordDecoder: Decoder[Record] = deriveDecoder[Record]
  given recordEncoder: Encoder[Record] = deriveEncoder[Record]
  given recordEntityEncoder[F[_]: Monad]: EntityEncoder[F, Record] = jsonEncoderOf[Record]
  given recordEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Record] = jsonOf[F, Record]
