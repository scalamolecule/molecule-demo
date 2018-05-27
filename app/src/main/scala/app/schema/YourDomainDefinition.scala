package app.schema
import molecule.schema.definition._


@InOut(0, 3)
object YourDomainDefinition {

  trait Person {
    val name     = oneString.fulltextSearch
    val age      = oneInt
    val gender   = oneEnum('male, 'female)
  }
}