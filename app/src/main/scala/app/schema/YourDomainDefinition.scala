package app.schema
import molecule.schema.definition._


@InOut(0, 3)
object YourDomainDefinition {

  trait Person {
    val name   = oneString.fulltext.doc("A Person's name")
    val age    = oneInt.doc("Age of person")
    val gender = oneEnum("male", "female").doc("Gender of person")
  }
}