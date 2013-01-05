package pl.project13.meetupgetnames

import data.Member
import util.Random

trait RandomWinnerSelection {

  import pl.project13.scala.rainbow._

  val random = new Random

  def selectNextWinner(members: List[Member]) {
    val leftPadding = "  "
    val line = "---------------------------------------"

    val winnerName = nextWinner(members).name

    def padded(s: String) = s.padTo(line.size - 3, ' ')

    println()
    println(leftPadding + line.green)
    println(leftPadding + s"| ${padded("and the winner is... ")} |".green)
    println(leftPadding + s"| ${padded("    "+winnerName).bold}".green + " |".green)
    println(leftPadding + line.green)
  }

  def nextWinner(members: List[Member]): Member = {
    val n = random.nextInt(members.size)
    members(n)
  }
}
