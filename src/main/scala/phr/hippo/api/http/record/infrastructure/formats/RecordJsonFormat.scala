package phr.hippo.api.http.record.infrastructure.formats

import cats.*
import cats.effect.Concurrent
import io.circe.*
import io.circe.generic.semiauto.*
import org.http4s.*
import org.http4s.circe.*
import phr.hippo.api.http.record.application.Commands.CreateRecord
import phr.hippo.api.http.record.domain.{ Record, RecordBody, RecordHeader }

import java.time.Instant
import java.util.UUID

object RecordJsonFormat:
  // Record Formats
  given recordDecoder: Decoder[Record] = deriveDecoder[Record]
  given recordEncoder: Encoder[Record] = deriveEncoder[Record]
  given recordEntityEncoder[F[_]: Monad]: EntityEncoder[F, Record] = jsonEncoderOf[Record]
  given recordEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Record] = jsonOf[F, Record]

  // CreateRecordFormats
  given createRecordDecoder: Decoder[CreateRecord] = deriveDecoder[CreateRecord]
  given createRecordEncoder: Encoder[CreateRecord] = deriveEncoder[CreateRecord]
  given createRecordEntityEncoder[F[_]: Monad]: EntityEncoder[F, CreateRecord] =
    jsonEncoderOf[CreateRecord]
  given createRecordEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, CreateRecord] =
    jsonOf[F, CreateRecord]
