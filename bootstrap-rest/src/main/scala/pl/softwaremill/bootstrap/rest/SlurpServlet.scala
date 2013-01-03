package pl.softwaremill.bootstrap.rest

import pl.softwaremill.bootstrap.service.meetup.MeetupNamesSlurper

class SlurpServlet(meetupNamesSlurper: MeetupNamesSlurper) extends JsonServlet {

  get("/") {

  }

  post("/") {
    val url = (parsedBody \ "url").extract[String]
    meetupNamesSlurper.slurpNames(url).sortBy(_.name)
  }

}
