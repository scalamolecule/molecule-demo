

lazy val commonSettings = Seq(
  organization := "com.yourcompany",
  version := "0.6.1",
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
    "org.scalamolecule" %% "molecule" % "0.6.1",
    "com.datomic" % "datomic-free" % "0.9.5359"
  )
)

lazy val root = project.in(file("."))
  .settings(moduleName := "demo-root")
  .aggregate(moleculeDemo)
  .settings(commonSettings)


lazy val moleculeDemo = project.in(file("demo"))
  .settings(moduleName := "moleculeDemo")
  .settings(commonSettings)

  // Add schema definition directories
  .settings(Seq(definitionDirectories(
    "demo/src/main/scala/demo"
  )))


def definitionDirectories(domainDirs: String*) = sourceGenerators in Compile += Def.task[Seq[File]] {
  val sourceDir = (sourceManaged in Compile).value

  // generate source files
  val sourceFiles = MoleculeBoilerplate.generate(sourceDir, domainDirs.toSeq)

  // Avoid re-generating boilerplate if nothing has changed when running `sbt compile`
  val cache = FileFunction.cached(
    streams.value.cacheDirectory / "moleculeBoilerplate",
    inStyle = FilesInfo.lastModified,
    outStyle = FilesInfo.hash
  ) {
    in: Set[File] => sourceFiles.toSet
  }
  cache(sourceFiles.toSet).toSeq
}.taskValue