package phr.hippo.api.http.record.infrastructure

import cats.effect.IO
import io.circe.Encoder
import io.circe.generic.semiauto.*
import org.http4s.circe.*
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.dsl.Http4sDsl
import org.http4s.syntax.kleisli.*
import org.http4s.{ EntityDecoder, EntityEncoder, Uri }
import org.scalatest.{ FunSuite, Matchers }
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class RecordRoutesSpec
    extends FunSuite
       with Matchers
       with ScalaCheckPropertyChecks
       with PetStoreArbitraries
       with Http4sDsl[IO]
       with Http4sClientDsl[IO]:
  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be(2)
    stack.pop() should be(1)
  }
