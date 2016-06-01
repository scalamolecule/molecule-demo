

lazy val commonSettings = Seq(
  organization := "com.yourcompany",
  version := "0.6.3",
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
    "org.scalamolecule" %% "molecule" % "0.6.2",
    "com.datomic" % "datomic-free" % "0.9.5359"
  )
)
lazy val defDirs = Seq("demo")

lazy val demo = project.in(file("."))
  .settings(moduleName := "molecule-demo-root")
  .aggregate(moleculeDemo)
  .settings(commonSettings)


lazy val moleculeDemo = project.in(file("demo"))
  .settings(moduleName := "molecule-demo")
  .settings(commonSettings)

  // Generate boilerplate
  .settings(definitionDirectories(defDirs))
  .settings(taskKey[Unit]("Create jar") <<= makeJar(defDirs))



def definitionDirectories(defDirs: Seq[String]) = sourceGenerators in Compile += Def.task[Seq[File]] {
  val codeDir = (scalaSource in Compile).value
  val managedDir = (sourceManaged in Compile).value
  val srcFiles = MoleculeBoilerplate.generate(codeDir, managedDir, defDirs)
  val cache = FileFunction.cached(
    streams.value.cacheDirectory / "moleculeBoilerplate",
    inStyle = FilesInfo.hash,
    outStyle = FilesInfo.hash
  ) {
    in: Set[File] => srcFiles.toSet
  }
  cache(srcFiles.toSet).toSeq
}.taskValue


def makeJar(domainDirs: Seq[String]) = Def.task {
  val sourceDir = (sourceManaged in Compile).value
  val targetDir = (classDirectory in Compile).value
  val moduleDirName = baseDirectory.value.toString.split("/").last

  // Create jar from generated source files
  val srcJar = new File(baseDirectory.value + "/lib/molecule-" + moduleDirName + "-sources.jar/")
  val srcFilesData = files2TupleRec("", sourceDir, ".scala")
  sbt.IO.jar(srcFilesData, srcJar, new java.util.jar.Manifest)

  // Create jar from class files compiled from generated source files
  val targetJar = new File(baseDirectory.value + "/lib/molecule-" + moduleDirName + ".jar/")
  val targetFilesData = files2TupleRec("", targetDir, ".class")
  sbt.IO.jar(targetFilesData, targetJar, new java.util.jar.Manifest)

  // Cleanup now obsolete generated code
  domainDirs.foreach { dir =>
    sbt.IO.delete(sourceDir / dir)
    sbt.IO.delete(targetDir / dir)
  }
}.triggeredBy(compile in Compile)


def files2TupleRec(pathPrefix: String, dir: File, tpe: String): Seq[Tuple2[File, String]] = {
  sbt.IO.listFiles(dir) flatMap { f =>
    if (f.isFile && f.name.endsWith(tpe))
      Seq((f, s"${pathPrefix}${f.getName}"))
    else
      files2TupleRec(s"${pathPrefix}${f.getName}/", f, tpe)
  }
}