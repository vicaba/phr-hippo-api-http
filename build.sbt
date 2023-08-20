ThisBuild / version := "0.1.0"

ThisBuild / scalaVersion := "3.3.0"

lazy val root = (project in file("."))
  .settings(
    name := "phr-hippo-api-http"
  )

ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision
scalacOptions ++= Seq(
  "-Wunused:all", // always on for OrganizeImports
  "-Ykind-projector",
  "-Ysafe-init",
  "-language:all",
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:postfixOps",
) ++
  Seq("-encoding", "UTF-8") ++
  Seq("-rewrite", "-indent") ++
  Seq("-source", "future-migration")
