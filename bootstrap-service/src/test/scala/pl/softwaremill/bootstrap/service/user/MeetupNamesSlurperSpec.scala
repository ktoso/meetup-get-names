package pl.softwaremill.bootstrap.service.user

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import pl.softwaremill.bootstrap.service.meetup.MeetupNamesSlurper

class MeetupNamesSlurperSpec extends Specification with Mockito {

  val url = "http://www.meetup.com/Krakow-Scala-User-Group/events/96668702/"
  val slurper = new MeetupNamesSlurper()


  "slurpNames" should {

    val names = slurper.slurpNames(url)

    println("names.head = " + names.head)

  }
}
