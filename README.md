# Molecule demo app

### Tryout a demo of Molecule

_See [Molecule](http://scalamolecule.org) website for more info._

Clone this repo and play around:

1. `git clone https://github.com/scalamolecule/molecule-demo.git`
2. `cd molecule-demo`
3. `sbt compile`
4. Open project in your IDE
5. Run DemoApp


### Molecule in your own project

For sbt 0.13.6+ add sbt-molecule as a dependency in `project/buildinfo.sbt`:

```scala
addSbtPlugin("org.scalamolecule" % "sbt-molecule" % "0.2.0")
```

Add the following in your `build.sbt`:

```scala
lazy val yourProject = project.in(file("demo"))
  .enablePlugins(MoleculePlugin)
  .settings(
    resolvers ++= Seq(
      "datomic" at "http://files.datomic.com/maven",
      "clojars" at "http://clojars.org/repo",
      Resolver.sonatypeRepo("releases"),
      "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"
    ),
    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "0.9.0",
      "com.datomic" % "datomic-free" % "0.9.5404"
    ),
    moleculeSchemas := Seq("app") // paths to your schema definition files...
  )
```


### Molecule Schema creation workflow

Try and add a new attribute to the demo schema:

  1. In IDE: add `val salary = oneDouble` to the `schema/YourDomainDefinition` trait
  2. In terminal: `sbt compile`
  2. Update some code with the new attribute - copy this if you like:
  
```scala
// Load data
val johnId = Person.name("John").age(26).gender("male").salary(100000.00).save.id

// Retrieve data
val (person, age, gender, salary) = Person.name.age.gender.salary.get.head

// Verify
assert(
s"$person is $age years old, $gender and earns $salary a year" ==
"John is 26 years old, male and earns 100000 a year"
)
```
      
Add more attributes and play around with queries...

