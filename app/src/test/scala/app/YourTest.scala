package app

import app.dsl.yourDomain._
import app.schema._
import molecule.datomic.api.out3._
import molecule.datomic.peer.facade.Datomic_Peer
import org.specs2.mutable.Specification


class YourTest extends Specification {


  "app" >> {
    YourApp.findLisa === ("Lisa", 27, "female")
  }


  "test" >> {
    // Make in-mem db
    implicit val conn = Datomic_Peer.recreateDbFrom(YourDomainSchema)

    // Save John
    Person.name("John").age(26).gender("male").save.eid

    // Retrieve John
    Person.name.age.gender.get.head === ("John", 26, "male")
  }
}
