package app.dataModel

import molecule.core.data.model._


@InOut(0, 6)
object YourDomainDataModel {

  trait Person {
    val name     = oneString.fulltext
    val age      = oneInt
    val gender   = oneEnum("male", "female")
  }
}