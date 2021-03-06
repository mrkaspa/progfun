package actor

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by michelperez on 3/7/15.
  */

class Toggle extends Actor {
  override def receive: Receive = happy

  def happy: Receive = {
    case "How are you?" =>
      sender ! "happy"
      context become sad
  }

  def sad: Receive = {
    case "How are you?" =>
      sender ! "sad"
      context become sad
  }
}