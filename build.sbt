import sbt.Keys._

lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "com.yourcompany",
  version := "0.15.0",
  scalaVersion := "2.12.7",
  scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
  resolvers ++= Seq(
    "datomic" at "http://files.datomic.com/maven",
    "clojars" at "http://clojars.org/repo",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.scalamolecule" %% "molecule" % "0.15.0",
    "com.datomic" % "datomic-free" % "0.9.5697"
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
  )
