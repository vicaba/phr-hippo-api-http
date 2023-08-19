package phr.hippo.api.http.record.infrastructure

import cats.effect.IO
import io.circe.Encoder
import io.circe.generic.semiauto.*
import org.http4s.circe.*
import org.http4s.client.dsl.Http4sClientDsl
import org.http4s.dsl.Http4sDsl
import org.http4s.syntax.kleisli.*
import org.http4s.{ EntityDecoder, EntityEncoder, Uri }

// https://www.youtube.com/watch?v=YO_hIUAxS60&t=4s
