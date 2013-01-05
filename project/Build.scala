import sbt._
import Keys._

object Resolvers {

  val myResolvers = Seq(
    "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/",
    "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
    "Twitter Maven" at "http://maven.twttr.com"
  )

}

object BuildSettings {

  import Dependencies._
  import Resolvers._

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "pl.project13",
    name := "meetupgetnames",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.10.0",

    resolvers := myResolvers,
    scalacOptions += "-unchecked",
    libraryDependencies ++= Seq(jsoup, words, rainbow) ++ jodaDependencies ++ testingDependencies
  )

}

object Dependencies {

  val words   = "pl.project13.scala" %% "words"   % "0.2"
  val rainbow = "pl.project13.scala" %% "rainbow" % "0.1"

  val jsoup = "org.jsoup" % "jsoup" % "1.7.1"

  val jodaTime = "joda-time" % "joda-time" % "2.0"
  val jodaConvert = "org.joda" % "joda-convert" % "1.2"

  val mockito = "org.mockito" % "mockito-all" % "1.9.5" % "test"
  val specs2 = "org.specs2" %% "specs2" % "1.12.3" % "test"

  val jodaDependencies = Seq(jodaTime, jodaConvert)
  val testingDependencies = Seq(mockito, specs2)

}

object MeetupGetNamesBuild extends Build {

  import BuildSettings._

  lazy val parent: Project = Project(
    "root",
    file("."),
    settings = buildSettings
  )

}
