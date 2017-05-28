import sbt.Keys._

lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "com.yourcompany",
  version := "0.12.0",
  scalaVersion := "2.12.2",
  scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
  resolvers ++= Seq(
    "datomic" at "http://files.datomic.com/maven",
    "clojars" at "http://clojars.org/repo",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.scalamolecule" %% "molecule" % "0.12.0",
    "com.datomic" % "datomic-free" % "0.9.5561"
  )
)

lazy val demo = project.in(file("."))
  .aggregate(app)
  .settings(commonSettings)


lazy val app = project.in(file("app"))
  .enablePlugins(MoleculePlugin)
  .settings(commonSettings)
  .settings(
    name := "molecule-demo",
    moleculeSchemas := Seq("app"),
    moleculeSeparateInFiles := false, // Optional to set
    moleculeAllIndexed := true // Optional to set
  )
