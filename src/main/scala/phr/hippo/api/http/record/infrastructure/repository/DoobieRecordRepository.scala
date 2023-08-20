package phr.hippo.api.http.record.infrastructure.repository

import doobie.implicits.*
import doobie.postgres.*
import doobie.postgres.implicits.*
import doobie.util.fragment.Fragment
import doobie.util.query.Query0
import doobie.util.transactor.Transactor
import doobie.util.update.Update0

import phr.hippo.api.http.record.domain.*

object DoobieRecordRepository extends App:
  def insert(r: Record): Fragment =
    sql"""|
          |INSERT INTO records
          |(id, created_at, updated_at, patient_id, headline, body)
          |VALUES
          |(${r.header.id}, ${r.header.createdAt}, ${r.header.updatedAt},
          |${r.header.patientId}, ${r.header.headline}, ${r.body.body})
       """.stripMargin

  println(insert(Record.dummy))
