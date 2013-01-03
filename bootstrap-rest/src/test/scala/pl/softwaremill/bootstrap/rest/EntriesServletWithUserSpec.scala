package pl.softwaremill.bootstrap.rest

import pl.softwaremill.bootstrap.service.user.UserService
import org.mockito.Matchers
import org.specs2.matcher.MatchResult
import pl.softwaremill.bootstrap.BootstrapServletSpec
import pl.softwaremill.bootstrap.common.Utils
import org.json4s.JsonDSL._
import org.json4s.JsonAST.JValue
import pl.softwaremill.bootstrap.service.data.{EntriesWithTimeStamp, UserJson, Member}
import pl.softwaremill.bootstrap.service.meetup.MeetupNamesSlurper


class EntriesServletWithUserSpec extends BootstrapServletSpec {

    def is =
      sequential ^
        "EntriesServlet with logged user" ^
        "POST / request should modify entry that user owns"       ! modifyExistingEntryThatLoggedUserOwns ^
        "POST / request should not update non existing entry"     ! notUpdateNonExistingEntry ^
        "POST / request should not update non owner entry"        ! notAllowToUpdateNotOwnedEntry ^
        "PUT / request should create new entry"                   ! shouldCreateNewEntry ^
    end

  val loginJasKowalski = "JasKowalski"

  val entryOne = Member("1", "Message from Jas", loginJasKowalski, "")
  val entryTwo = Member("2", "Message 2 from Jas", loginJasKowalski, "")

  def onServletWithMocks(login: String, testToExecute: (MeetupNamesSlurper, UserService) => MatchResult[Any]): MatchResult[Any] = {
    val userService = mock[UserService]

    val entryService = mock[MeetupNamesSlurper]
    entryService.count() returns 2
    entryService.loadAll returns EntriesWithTimeStamp(List(entryOne, entryTwo))
    entryService.load("1") returns Some(entryOne)
    entryService.load("2") returns Some(entryTwo)
    entryService.load("3") returns None
    entryService.isAuthor(loginJasKowalski, "1") returns true
    entryService.isAuthor(loginJasKowalski, "2") returns true

    val servlet: SlurpServlet = new EntriesServletWithUser(entryService, userService, login)
    addServlet(servlet, "/*")

    testToExecute(entryService, userService)
  }

  def modifyExistingEntryThatLoggedUserOwns = onServletWithMocks(login = loginJasKowalski, testToExecute = (entryService, userService) =>
    put("/", mapToJson(Map[String, JValue]("id" -> "1", "text" -> "Important message")), defaultJsonHeaders) {
      there was one(entryService).isAuthor(loginJasKowalski, "1")
      there was one(entryService).update("1", "Important message")
      status must_== 200
    }
  )

  def notUpdateNonExistingEntry = onServletWithMocks(login = loginJasKowalski, testToExecute = (entryService, userService) =>
    put("/", mapToJson(Map[String, JValue]("id" -> "3", "text"-> "Important message")), defaultJsonHeaders) {
      there was one(entryService).isAuthor(loginJasKowalski, "3")
      there was no(entryService).update(anyString, anyString)
      status must_== 403
    }
  )

  def notAllowToUpdateNotOwnedEntry = onServletWithMocks(login = "PiotrNowak", testToExecute = (entryService, usersService) =>
    put("/", mapToJson(Map[String, JValue]("id" -> "2", "text"-> "Important message")), defaultJsonHeaders) {
      there was one(entryService).isAuthor("PiotrNowak", "2")
      there was no(entryService).update(anyString, anyString)
      status must_== 403
    }
  )

  def shouldCreateNewEntry = onServletWithMocks(login = loginJasKowalski, testToExecute = (entryService, userService) =>
    post("/", mapToJson(Map("text"-> "New message")), defaultJsonHeaders) {
      there was one(entryService).add(loginJasKowalski, "New message")
      there was no(entryService).update(anyString, anyString)
      status must_== 200
    }
  )

}

class EntriesServletWithUser(entryService: MeetupNamesSlurper, userService: UserService, login: String) extends SlurpServlet(entryService, userService) {

  override def isAuthenticated = true

  override def user = new UserJson(login, Utils.sha256("password", login))

}
