val Http4sVersion = "1.0.0-M40"
val CirceVersion = "0.14.3"
val ScalaCheckVersion = "1.14.0"
val ScalaTestVersion = "3.2.16"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "org.http4s" %% "http4s-ember-client" % Http4sVersion % Test,
  // "org.scalacheck" %% "scalacheck" % ScalaCheckVersion % Test,
  // "org.scalatest" %% "scalatest" % ScalaTestVersion % Test,
)
