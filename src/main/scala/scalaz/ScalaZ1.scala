package scalaz

import scalaz._
import Scalaz._

/**
 * Created by michelperez on 4/17/15.
 */
object ScalaZ1 {

  trait CanTruthy[A] {
    self =>
    /** @return true, if `a` is truthy. */
    def truthys(a: A): Boolean
  }

  object CanTruthy {
    def apply[A](implicit ev: CanTruthy[A]): CanTruthy[A] = ev

    def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
      def truthys(a: A): Boolean = f(a)
    }
  }

  trait CanTruthyOps[A] {
    def self: A

    implicit def F: CanTruthy[A]

    final def truthy: Boolean = F.truthys(self)
  }

  object ToCanIsTruthyOps {
    implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
      new CanTruthyOps[A] {
        def self = v

        implicit def F: CanTruthy[A] = ev
      }
  }

}

object Main1 extends App {

  import ScalaZ1._
  import ToCanIsTruthyOps._

  implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
    case 0 => false
    case _ => true
  })

  10.truthy

  implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
    case Nil => false
    case _ => true
  })

  implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)

  List(1, 2).truthy

  implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)

  implicit val stringCanTruthy: CanTruthy[String] = CanTruthy.truthys({
    case "" => false
    case _ => true
  })

  "".truthy.println

  def ifk[A: CanTruthy, B, C](cond: A)(yes: => B)(not: => C) = {
    if (cond.truthy) yes else not
  }

  def ifr[A: CanTruthy, B](cond: A)(yes: => B)(not: => B): B = {
    if (cond.truthy) yes else not
  }

  ifk("") {
    println("NO EMPTY")
  } {
    println("EMPTY")
  }

  val str: String = ifr("123") {
    "NO EMPTY"
  } {
    "EMPTY"
  }

  println(str)

}
