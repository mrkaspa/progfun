package week8

import akka.actor.{ActorSystem, Actor, Props}
import akka.actor.Actor.Receive

/**
 * Created by michelperez on 2/27/15.
 */
object CounterMain extends App {

  class Counter extends Actor {
    var count = 0

    override def receive: Receive = {
      case "incr" => count += 1
      case "get" => sender ! count
    }
  }

  class Main extends Actor {
    val counter = context.actorOf(Props[Counter], "counter")

    counter ! "incr"
    counter ! "incr"
    counter ! "incr"
    counter ! "get"

    override def receive: Actor.Receive = {
      case count: Int =>
        println(s"count was $count")
        context.stop(self)
    }
  }

  val system = ActorSystem("system")

  val mainActor = system.actorOf(Props[Main])

}
