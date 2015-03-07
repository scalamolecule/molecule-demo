package demo

import demo.dsl.yourDomain._
import demo.schema._
import molecule._

object YourApp extends App with DatomicFacade {

  // Make db
  implicit val conn = load(YourDomainSchema.tx)

  // Load data
  val companyId = Person.name("John").age(26).gender("male").add.id

  // Retrieve data
  val (person, age, gender) = Person.name.age.gender.one

  // Verify
  assert(s"$person is $age years old and $gender" == "John is 26 years old and male")
  println(s"SUCCESS: $person is $age years old and $gender") //SUCCESS: John is 26 years old and male
}
