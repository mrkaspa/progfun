package week8

import akka.actor.{Props, ActorSystem}
import akka.testkit.{ImplicitSender, TestKit, TestProbe}
import org.scalatest.FunSuite
import scala.concurrent.duration._

/**
 * Created by michelperez on 3/8/15.
 */
class ToggleTest extends FunSuite {

  implicit val system = ActorSystem("TestSys")

  val toggle = system.actorOf(Props[Toggle])
  val p = TestProbe()

  p.send(toggle, "How are you?")
  p.expectMsg("happy")
  p.send(toggle, "How are you?")
  p.expectMsg("sad")
  p.send(toggle, "unknown")
  p.expectNoMsg(1 second)

  system.shutdown()

  new TestKit(ActorSystem("TestSys")) with ImplicitSender {
    val toggle = system.actorOf(Props[Toggle])

    toggle ! "How are you?"
    expectMsg("happy")
    toggle ! "How are you?"
    expectMsg("sad")
    toggle ! "unknown"
    expectNoMsg(1 second)

    system.shutdown()
  }

}
