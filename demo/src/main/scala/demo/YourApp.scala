package demo

import demo.dsl.yourDomain._
import demo.schema._
import molecule._

object YourApp extends App with DatomicFacade {

  // Make db
  implicit val conn = load(YourDomainSchema)

  // Load data
  val companyId = Person.name("John").age(26).gender("male").add.eid

  // Retrieve data
  val (person, age, gender) = Person.name.age.gender.one

  // Verify
  assert(s"$person is a $age years old $gender" == "John is a 26 years old male")
  println(s"SUCCESS: $person is a $age years old $gender") //SUCCESS: John is a 26 years old male
}
