package future

import rx.lang.scala.{Subscription, Observable}
import scala.language.postfixOps
import scala.concurrent.duration._

/**
 * Created by michelperez on 2/25/15.
 */
object ObservablesExample extends App {

  val names = List("demo")
  Observable.from(names) subscribe { n =>
    println(s"Hello $n!")
  }

  val xs: Observable[Int] = Observable.from(List(3, 2, 1))
  val yss: Observable[Observable[Int]] = xs.map(x => Observable.interval(x seconds).map(_ => x).take(2))
  val zs: Observable[Int] = yss.flatten //gets the value as it executes
  //val zs = yss.concat //gets the value by the input order

  zs subscribe { n =>
    println(s"XXX >> $n!")
  }

  println("Antes d edormir")
  Thread.sleep(10000)
  println("Despues d edormir")

  val obs = Observable[Int](observer => {
    observer.onNext(42)
    observer.onNext(44)
    observer.onCompleted()
    observer.onNext(4711)
    Subscription {}
  })

  obs subscribe {
    println(_)
  }

}
