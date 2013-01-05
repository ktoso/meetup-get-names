package pl.project13.meetupgetnames

import data.Member
import pl.project13.scala.words.verbs.AskVerb
import service.MeetupSlurper
import annotation.tailrec
import pl.project13.scala.rainbow._

object GetMeetupNamesApp extends App
  with RandomWinnerSelection with AskVerb {

  val MeetupUrl = """http://www.meetup.com/.+/events/(\d+)/?""".r

  val slurper = new MeetupSlurper

  // ---

  val url = if (args.size == 1)
      args.head
    else
      askForMeetupUrl()

  val allMembers = slurper.slurpMembers(url)

  println("All attendees: ".bold)
  allMembers foreach { m => println(m.name) }

  println()

  raffleForPrize(allMembers)

  // ---
  @tailrec def askForMeetupUrl(): String = {
    val got = askForString("Meetup URL: ")

    if (MeetupUrl.pattern.matcher(got).matches) got
    else askForMeetupUrl()
  }

  @tailrec def raffleForPrize(members: List[Member]) {
    ask("Randomly select a participant?", Yes) match {
      case No  => ()
      case Yes => selectNextWinner(members); raffleForPrize(members)
    }
  }
}
