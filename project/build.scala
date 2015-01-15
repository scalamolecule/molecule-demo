import sbt.Keys._
import sbt._


object MoleculeDemoBuild extends Build with Boilerplate {

  lazy val moleculeDemo = Project(
    id = "moleculeDemo",
    base = file("demo"),
    settings = commonSettings ++ Seq(
      moleculeDefinitionDirectories(
        "demo/src/main/scala/demo"
      )
    )
  )

  lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
    organization := "com.yourcompany",
    version := "0.1.0",
    scalaVersion := "2.11.4",
    scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
    resolvers ++= Seq(
      "datomic" at "http://files.datomic.com/maven",
      "clojars" at "http://clojars.org/repo",
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots"),
      "Typesafe Simple Repository" at "http://repo.typesafe.com/typesafe/simple/maven-releases/",
      "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
    ),
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      // "org.scala-sbt" % "io" % "0.13.7",
      "org.scalamolecule" % "molecule_2.11" % "0.2.1",
      "commons-codec" % "commons-codec" % "1.10",
      "com.datomic" % "datomic-free" % "0.9.5067",
      "com.chuusai" %% "shapeless" % "2.0.0"
    )
    //    fork := true,
    //    javaOptions += "-Xmx16G -Xss1G -Xms500m -Xmn5m -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled",

    //,moleculeTask <<= moleculeCodeGenTask // register manual sbt command
    //,sourceGenerators in Compile <+= moleculeCodeGenTask // register automatic code generation on every compile, remove for only manual use
  )

  //  // code generation task
  //  lazy val moleculeTask        = TaskKey[Seq[File]]("gen-tables")
  //  lazy val moleculeCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (srcDir, cp, r, s) =>
  // // lazy val moleculeCodeGenTask = (sourceManaged in Compile, dependencyClasspath in Compile, runner in Compile, streams) map { (srcDir, cp, r, s) =>
  //    val domainDirs: Seq[String] = Seq(
  //        "demo/src/main/scala/demo"
  //    )
  //    // Generate files
  //    toError(r.run("molecule.util.BoilerplateGenerator", cp.files, (srcDir.getPath +: domainDirs).toArray[String], s.log))
  //    Seq[File]()
  //  }
}

trait Boilerplate {
  def moleculeDefinitionDirectories(domainDirs: String*) = sourceGenerators in Compile += Def.task[Seq[File]] {
    val sourceDir = (sourceManaged in Compile).value

    // generate source files
    val sourceFiles = DslBoilerplate.generate(sourceDir, domainDirs.toSeq)

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
}