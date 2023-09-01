val Http4sVersion = "1.0.0-M40"
val CirceVersion = "0.14.5"
val MUnitVersion = "0.7.29"
val MUnitHttp4sVersion = "0.15.0"
val DoobieVersion = "1.0.0-RC4"
val NewTypeVersion = "0.4.4"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-server" % Http4sVersion,
  "org.http4s" %% "http4s-circe" % Http4sVersion,
  "org.http4s" %% "http4s-dsl" % Http4sVersion,
  "io.circe" %% "circe-generic" % CirceVersion,
  "org.tpolecat" %% "doobie-core" % DoobieVersion,
  "org.tpolecat" %% "doobie-postgres" % DoobieVersion,
  "org.tpolecat" %% "doobie-hikari" % DoobieVersion,
  "org.slf4j" % "slf4j-simple" % "2.0.7",
  "org.http4s" %% "http4s-ember-client" % Http4sVersion % Test,
  "org.scalameta" %% "munit" % MUnitVersion % Test,
  "com.alejandrohdezma" %% "http4s-munit" % "0.15.0" % Test,
)
