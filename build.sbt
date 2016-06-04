lazy val commonSettings = Seq(
  organization := "com.yourcompany",
  version := "0.7.0",
  scalaVersion := "2.11.8",
  scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
  resolvers ++= Seq(
    "datomic" at "http://files.datomic.com/maven",
    "clojars" at "http://clojars.org/repo",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots"),
    "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
  ),
  libraryDependencies ++= Seq(
    "org.scalamolecule" %% "molecule" % "0.7.0",
    "com.datomic" % "datomic-free" % "0.9.5359"
  )
)

lazy val demo = project.in(file("."))
  .aggregate(moleculeDemo)
  .settings(commonSettings)
  .settings(moduleName := "molecule-demo-root")


lazy val moleculeDemo = project.in(file("demo"))
  .enablePlugins(MoleculePlugin)
  .settings(commonSettings)
  .settings(
    moduleName := "molecule-demo",
    moleculeSchemas := Seq("demo")
  )
