package week8

import akka.actor.{ActorSystem, Actor, Props, ActorRef}
import akka.actor.Actor.Receive
import akka.event.LoggingReceive

/**
 * Created by michelperez on 3/7/15.
 */
object BankAccount {

  case class Deposit(ammount: BigInt) {
    require(ammount > 0)
  }

  case class Withdraw(ammount: BigInt) {
    require(ammount > 0)
  }

  case object Done

  case object Failed

}

class BankAccount extends Actor {

  import BankAccount._

  var balance = BigInt(0)

  override def receive: Receive = LoggingReceive {
    case Deposit(amount) =>
      balance += amount
      sender ! Done
    case Withdraw(amount) if amount <= balance =>
      balance -= amount
      sender ! Done
    case _ =>
      sender ! Failed
  }
}

object WireTransfer {

  case class Transfer(from: ActorRef, to: ActorRef, amount: BigInt)

  case object Done

  case object Failed

}


class WireTransfer extends Actor {

  import WireTransfer._

  override def receive: Actor.Receive = LoggingReceive {
    case Transfer(from, to, amount) =>
      from ! BankAccount.Withdraw(amount)
      context.become(awaitWithdraw(to, amount, sender))
  }

  def awaitWithdraw(to: ActorRef, amount: BigInt, client: ActorRef): Receive = LoggingReceive {
    case BankAccount.Done =>
      to ! BankAccount.Deposit(amount)
      context.become(awaitDeposit(client))
    case BankAccount.Failed =>
      client ! Failed
      context.stop(self)
  }

  def awaitDeposit(client: ActorRef): Receive = LoggingReceive {
    case BankAccount.Done =>
      client ! Done
      context.stop(self)
  }

}

class TransferMain extends Actor {
  val accountA = context.actorOf(Props[BankAccount], "accountA")
  val accountB = context.actorOf(Props[BankAccount], "accountB")

  accountA ! BankAccount.Deposit(100)

  override def receive: Actor.Receive = LoggingReceive {
    case BankAccount.Done => transfer(50)
  }

  def transfer(ammount: BigInt): Unit = {
    val transaction = context.actorOf(Props[WireTransfer], "transfer")
    transaction ! WireTransfer.Transfer(accountA, accountB, ammount)
    context.become(waitForFinishedTransfer())
  }

  def waitForFinishedTransfer(): Receive = LoggingReceive {
    case WireTransfer.Done =>
      println("success")
      context.stop(self)
    case WireTransfer.Failed =>
      println("error")
      context.stop(self)
  }

}

object Bank extends App {

  val system = ActorSystem("system")

  val mainActor = system.actorOf(Props[TransferMain])

}
