package phr.hippo.api.http.record.infrastructure.formats

import cats.*
import cats.effect.Concurrent

import io.circe.*
import io.circe.generic.semiauto.*
import org.http4s.*
import org.http4s.circe.*

import phr.hippo.api.http.record.domain.Record

object RecordJsonFormat:
  given Decoder[Record] = deriveDecoder[Record]
  given Encoder[Record] = deriveEncoder[Record]
  given recordEntityEncoder[F[_]: Monad]: EntityEncoder[F, Record] = jsonEncoderOf[Record]
  given recordEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Record] = jsonOf[F, Record]
