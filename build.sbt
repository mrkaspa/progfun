name := "ProgFun"

version := "1.0"

scalaVersion := "2.11.5"

val scalazVersion = "7.1.1"

libraryDependencies ++= Seq(
  "io.reactivex" %% "rxscala" % "0.23.1",
  "com.typesafe.akka" %% "akka-actor" % "2.3.6",
  "com.ning" % "async-http-client" % "1.9.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test",
  "org.slf4j" % "slf4j-simple" % "1.7.10",
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-effect" % scalazVersion,
  "org.scalaz" %% "scalaz-typelevel" % scalazVersion,
  "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test"
)

initialCommands in console := "import scalaz._, Scalaz._"

initialCommands in console in Test := "import scalaz._, Scalaz._, scalacheck.ScalazProperties._, scalacheck.ScalazArbitrary._,scalacheck.ScalaCheckBinding._"