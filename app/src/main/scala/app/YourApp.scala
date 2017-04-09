package app

import app.dsl.yourDomain._
import app.schema._
import molecule._


object YourApp extends App with DatomicFacade {

  // Make db
  implicit val conn = recreateDbFrom(YourDomainSchema)

  // Load data
  val johnId = Person.name("John").age(26).gender("male").save.eid


  // Retrieve data
  val (person, age, gender) = Person.name.age.gender.one

  // Verify
  assert(s"$person is a $age years old $gender" == "John is a 26 years old male")

  println(s"SUCCESS: $person is a $age years old $gender") //SUCCESS: John is a 26 years old male
}
