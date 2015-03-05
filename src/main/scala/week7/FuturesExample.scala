package week7

import rx.lang.scala.Observable

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Success
import scala.util.Failure

object FuturesExample extends App {

  val fut = Future {
    Thread.sleep(100)
    123
  }

  println("START...")
//
//  fut onComplete {
//    case Success(num) => println(s"FINISH >> $num")
//    case Failure(e) => println("error...")
//  }

  val obs = Observable.from(fut)

  obs subscribe { n =>
    println(s"XXX >> $n!")
  }

  Thread.sleep(200)



}