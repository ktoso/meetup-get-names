package pl.softwaremill.bootstrap.service.meetup

import org.jsoup.Jsoup
import collection.JavaConversions._
import org.jsoup.nodes.Element
import pl.softwaremill.bootstrap.service.data.Member

class MeetupNamesSlurper {

  def slurpNames(url: String): List[Member] = {
    val doc = Jsoup.connect(url).get()
    val rsvpElements = doc.select("#rsvplist_yes li")
    rsvpElements.iterator.map(rsvpElementToName).toList
  }


  private def rsvpElementToName(el: Element) = {
    val id = el select (".figureset-description") attr ("id") replaceAll("post", "")
    val name = el select ("span.linked") text
    val imgUrl = el select ("img") attr ("src")
    val profileUrl = el select ("a") attr ("href")

    Member(id, name, imgUrl, profileUrl)
  }
}
