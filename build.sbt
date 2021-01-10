import sbt.Keys._

lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "com.yourcompany",
  version := "0.23.1",
  scalaVersion := "2.13.4",
  scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
  resolvers ++= Seq(
//    ("datomic" at "http://files.datomic.com/maven").withAllowInsecureProtocol(true),
    ("clojars" at "http://clojars.org/repo").withAllowInsecureProtocol(true),
  ),
  libraryDependencies ++= Seq(
    "org.scalamolecule" %% "molecule" % "0.23.1",
    "com.datomic" % "datomic-free" % "0.9.5697",
    "org.specs2" %% "specs2-core" % "4.10.5"
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

    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule") == Some("true"),
    moleculeDataModelPaths := Seq("app"), // path to domain model directory

    // Let IDE detect created jars in unmanaged lib directory
    exportJars := true
  )
