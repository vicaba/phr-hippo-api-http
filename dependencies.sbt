val Http4sVersion = "1.0.0-M40"
val CirceVersion = "0.14.3"
val MUnitVersion = "0.7.29"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "org.http4s" %% "http4s-ember-client" % Http4sVersion % Test,
  "org.scalameta" %% "munit" % MUnitVersion % Test,
)
