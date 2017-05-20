# Molecule demo app

### Tryout a demo of Molecule

_See [Molecule](http://scalamolecule.org) website for more info._

Clone this repo and play around:

1. `git clone https://github.com/scalamolecule/molecule-demo.git`
2. `cd molecule-demo`
3. `sbt compile`
4. Open project in your IDE (you might need to refresh the SBT project within the IDE)
5. Run DemoApp


### Molecule in your own project

Add the following to your build files: 

`project/build.properties`:

```scala
sbt.version=0.13.13
```

`project/buildinfo.sbt`:

```scala
addSbtPlugin("org.scalamolecule" % "sbt-molecule" % "0.3.4")
```

`build.sbt`:

```scala
lazy val yourProject = project.in(file("demo"))
  .enablePlugins(MoleculePlugin)
  .settings(
    resolvers ++= Seq(
      "datomic" at "http://files.datomic.com/maven",
      "clojars" at "http://clojars.org/repo",
      Resolver.sonatypeRepo("releases")
    ),
    libraryDependencies ++= Seq(
      "org.scalamolecule" %% "molecule" % "0.11.0",
      "com.datomic" % "datomic-free" % "0.9.5561"
    ),
    moleculeSchemas := Seq("demo") // paths to your schema definition files...
  )
```
(You might need to mark the `lib` folder as a library in your IDE so that it recognizes the created boilerplate class/source jars)

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

