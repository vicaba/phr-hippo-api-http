package phr.hippo.api.http.record.domain

import java.time.Instant
import java.util.UUID

case class RecordHeader(
  id: UUID,
  createdAt: Instant,
  updatedAt: Instant,
  patientId: UUID,
  headline: String,
)

case class RecordBody(
  body: String
)

case class Record(
  header: RecordHeader,
  body: RecordBody,
)

object Record:
  // TODO: Method signature should be changed
  def dummy: Record =
    def dummyHeader: RecordHeader =
      RecordHeader(
        UUID.randomUUID(),
        Instant.now(),
        Instant.now(),
        UUID.randomUUID(),
        "Back pain",
      )
    def dummyBody: RecordBody =
      RecordBody(
        "The back pain was sudden, just when I woke up today."
      )
    Record(
      dummyHeader,
      dummyBody,
    )
