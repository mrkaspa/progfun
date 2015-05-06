package week9

/**
 * Created by michelperez on 3/11/15.
 */

import akka.actor.{ActorSystem, Actor, Props, Terminated}

class WatchActor extends Actor {
  val child = context.actorOf(Props.empty, "child")
  context.watch(child)
  // <-- this is the only call needed for registration

  var lastSender = context.system.deadLetters

  def receive = {
    case "kill" =>
      println("killing child")
      context.stop(child);
      lastSender = sender()
    case Terminated =>
      println("child death")
      lastSender ! "finished"
  }
}

object Main extends App {
  val system = ActorSystem("system")

  val mainActor = system.actorOf(Props[WatchActor], "main")

  mainActor ! "kill"
}