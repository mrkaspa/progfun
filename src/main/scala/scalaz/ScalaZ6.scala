package scalaz

import scalaz._
import Scalaz._

/**
 * Created by michelperez on 4/21/15.
 */
object ScalaZ6 extends App {

//  def logNumber(x: Int): Writer[List[String], Int] =
//    x.set(List("Got number: " + x.shows))
//
//  def multiWithLog: Writer[List[String], Int] = for {
//    a <- logNumber(1)
//    b <- logNumber(2)
//    c <- logNumber(3)
//  } yield a * b * c
//
//  val (a, b) = multiWithLog.run
//  println(b)

  //benchmark with writer
//  def vectorFinalCountDown(x: Int): Writer[Vector[String], Unit] = {
//    import annotation.tailrec
//    @tailrec def doFinalCountDown(x: Int, w: Writer[Vector[String], Unit]): Writer[Vector[String], Unit] = x match {
//      case 0 => w flatMap  { _ => Vector("0").tell }
//      case x => doFinalCountDown(x - 1, w flatMap { _ =>
//        Vector(x.shows).tell
//      })
//    }
//    val t0 = System.currentTimeMillis
//    val r = doFinalCountDown(x, Vector[String]().tell)
//    val t1 = System.currentTimeMillis
//    r flatMap { _ => Vector((t1 - t0).shows + " msec").tell }
//  }
//
//  println(vectorFinalCountDown(10).run)

}
