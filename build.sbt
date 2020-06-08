import sbt.Keys._

lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "com.yourcompany",
  version := "0.22.3",
  scalaVersion := "2.13.2",
  scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
  resolvers ++= Seq(
    ("datomic" at "http://files.datomic.com/maven")
      .withAllowInsecureProtocol(true),
    ("clojars" at "http://clojars.org/repo")
      .withAllowInsecureProtocol(true),
  ),
  libraryDependencies ++= Seq(
    "org.scalamolecule" %% "molecule" % "0.22.3",
    "com.datomic" % "datomic-free" % "0.9.5697"
  )
)

lazy val demo = project.in(file("."))
  .aggregate(app)
  .settings(commonSettings)
  .settings(name := "molecule-demo")

lazy val app = project.in(file("app"))
  .enablePlugins(MoleculePlugin)
  .settings(commonSettings)
  .settings(
    name := "molecule-demo-app",
    moleculeSchemas := Seq("app"),
  )
