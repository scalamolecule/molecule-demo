import sbt.Keys._
import sbt._


object MoleculeDemoBuild extends Build with Boilerplate {


  lazy val molecule = Project(
    id = "molecule",
    base = file("."),
    aggregate = Seq(moleculeDemo),
    settings = commonSettings ++ Seq(
      moduleName := "molecule-root"
    )
  )

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
    version := "0.3.0",
    scalaVersion := "2.11.6",
    scalacOptions := Seq("-feature", "-language:implicitConversions", "-Yrangepos"),
    resolvers ++= Seq(
      "datomic" at "http://files.datomic.com/maven",
      "clojars" at "http://clojars.org/repo",
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),
    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "0.3.0",
      "com.datomic" % "datomic-free" % "0.9.5206",
      "com.chuusai" %% "shapeless" % "2.0.0"
    )
  )
}

trait Boilerplate {
  def moleculeDefinitionDirectories(domainDirs: String*) = sourceGenerators in Compile += Def.task[Seq[File]] {
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
}