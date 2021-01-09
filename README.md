# Molecule demo app

### Try Molecule demo

_See [Molecule](http://scalamolecule.org) website for more info._



1. `git clone https://github.com/scalamolecule/molecule-demo.git`
2. `cd molecule-demo`
3. `sbt compile`
4. Open project in your IDE
5. `sbt test` or test in your IDE


### Molecule in your own project

Add the following to your build files: 

`project/build.properties`:

```scala
sbt.version=1.4.6
```

`project/buildinfo.sbt`:

```scala
addSbtPlugin("org.scalamolecule" % "sbt-molecule" % "0.12.0")
```

`build.sbt`:

```scala
lazy val yourProject = project.in(file("app"))
  .enablePlugins(MoleculePlugin)
  .settings(
    resolvers ++= Seq(
      "datomic" at "http://files.datomic.com/maven",
      "clojars" at "http://clojars.org/repo",
      Resolver.sonatypeRepo("releases")
    ),
    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "0.23.1",
      "com.datomic" % "datomic-free" % "0.9.5697"
    ),
    
    // Generate Molecule boilerplate code with `sbt clean compile -Dmolecule=true`
    moleculePluginActive := sys.props.get("molecule") == Some("true"),
    moleculeDataModelPaths := Seq("app"), // paths to your schema definition files...
    
    // Let IDE detect created jars in unmanaged lib directory
    exportJars := true
  )
```
Molecule cross-compilations available at maven central for Scala 
[2.13](https://oss.sonatype.org/content/repositories/releases/org/scalamolecule/sbt-molecule_2.13.1/) and
[2.12](https://oss.sonatype.org/content/repositories/releases/org/scalamolecule/sbt-molecule_2.12.10/).

