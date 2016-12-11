package actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by michelperez on 2/27/15.
  */
object CounterMain extends App {

  class Counter extends Actor {
    var count = 0

    override def receive: Receive = {
      case "incr" => count += 1
      case ("get", actor: ActorRef) => actor ! count
    }
  }

  class Main extends Actor {
    override def receive: Actor.Receive = {
      case count: Int =>
        println(s"count was $count")
        context.stop(self)
    }
  }

  val system = ActorSystem("system")
  val counterActor = system.actorOf(Props[Counter])
  val mainActor = system.actorOf(Props[Main])

  counterActor ! "incr"
  counterActor ! "incr"
  counterActor ! "incr"
  counterActor ! ("get", mainActor)
}
