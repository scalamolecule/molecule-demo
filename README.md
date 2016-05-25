# Molecule demo app

####Minimal project setup for using Molecule

_See [Molecule](http://scalamolecule.org) website for more info._

Clone this repo and play around:

1. `git clone https://github.com/scalamolecule/molecule-demo.git`
2. `cd molecule-demo`
3. `sbt compile`
4. Open project in your IDE
5. Run DemoApp


### Molecule Schema creation workflow

Try and add a new attribute to your schema:

  1. In IDE: add `val salary = oneDouble` to the `schema/YourDomainDefinition` trait
  2. In terminal: `sbt compile`
  2. Update some code with the new attribute - copy this if you like:
  
```scala
// Load data
val johnId = Person.name("John").age(26).gender("male").salary(100000.00).add.id

// Retrieve data
val (person, age, gender, salary) = Person.name.age.gender.salary.get.head

// Verify
assert(
s"$person is $age years old, $gender and earns $salary a year" ==
"John is 26 years old, male and earns 100000 a year"
)
```
      
Add more attributes and play around with queries...


### Molecule in your own project

In the project build file you can see what you need to add to your own project
in order to use Molecule. The Molecule dependency itself is

```scala
"org.scalamolecule" %% "molecule" % "0.6.1"
```

But you'll also need the Boilerplate trait in your build so that you can tell Molecule 
in which directories you have schema definition files (using the `moleculeDefinitionDirectories` method):

```
// Add schema definition directories
.settings(Seq(moleculeDefinitionDirectories(
  "demo/src/main/scala/demo"
)))
```