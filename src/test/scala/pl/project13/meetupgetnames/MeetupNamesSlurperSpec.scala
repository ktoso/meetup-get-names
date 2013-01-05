package pl.project13.meetupgetnames

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import service.MeetupSlurper

class MeetupNamesSlurperSpec extends Specification with Mockito {

  val slurper = new MeetupSlurper()


  "slurpNames" should {
    "fetch members from a live meetup site" in {
      // given
      val url = "http://www.meetup.com/Krakow-Scala-User-Group/events/96668702/"

      // when
      val members = slurper.slurpMembers(url)

      // then
      members.length must be_>(0)

      val names = members.map(_.name)
      names must contain ("Konrad Malawski")
    }
  }
}
