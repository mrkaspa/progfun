package week7

import rx.lang.scala.subjects.PublishSubject

/**
 * Created by michelperez on 2/26/15.
 */
object SubjectsExamples extends App {

  val channel = PublishSubject[Int]()

  val a = channel.subscribe(x => println("a: " + x))
  val b = channel.subscribe(x => println("b: " + x))

  channel.onNext(42)
  a.unsubscribe()
  channel.onNext(4711)
  channel.onCompleted()

  val c = channel.subscribe(x => println("c: " + x))

}
