import sbt.Keys._

lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "com.yourcompany",
  version := "0.10.3",
  scalaVersion := "2.12.1",
  scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
  resolvers ++= Seq(
    "datomic" at "http://files.datomic.com/maven",
    "clojars" at "http://clojars.org/repo",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
  ),
  libraryDependencies ++= Seq(
    "org.scalamolecule" %% "molecule" % "0.10.3",
    "com.datomic" % "datomic-free" % "0.9.5561",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
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
