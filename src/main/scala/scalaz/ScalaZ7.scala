package scalaz

import scalaz._
import Scalaz._

/**
 * Created by michelperez on 4/22/15.
 */
object ScalaZ7 extends App {

  type Stack = List[Int]

  def pop = State[Stack, Int] {
    case x :: xs => (xs, x)
  }

  def push(a: Int) = State[Stack, Unit] {
    case xs => (a :: xs, ())
  }

  val pushIPop = for {
    _ <- push(3)
    a <- pop
    b <- pop
  } yield (b)

  println(pushIPop(List(1)))

  def popFor: State[Stack, Int] = for {
    s <- get[Stack]
    (x :: xs) = s
    _ <- put(xs)
  } yield x

  def pushFor(x: Int): State[Stack, Unit] = for {
    xs <- get[Stack]
    r <- put(x :: xs)
  } yield r

  case class User(id: Option[Int])

  def init: State[User, Unit] = for {
    u <- get[User]
    r <- if (u == User(None)) put(User(Some(1))) else put(u)
  } yield r

  println(s"user id  null ${init(User(None))._1}")
  println(s"user id not null ${init(User(Some(123)))._1}")

}