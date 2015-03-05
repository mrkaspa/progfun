name := "ProgFun"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "io.reactivex" %% "rxscala" % "0.23.1",
  "com.typesafe.akka" %% "akka-actor" % "2.3.6",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.6" % "test",
  "org.scalatest" %% "scalatest" % "2.1.6" % "test"
)

