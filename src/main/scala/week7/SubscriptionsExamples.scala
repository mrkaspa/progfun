package week7

import scala.language.postfixOps
import scala.concurrent.duration._
import rx.lang.scala.subscriptions.{MultipleAssignmentSubscription, CompositeSubscription, BooleanSubscription}
import rx.lang.scala.{Subscription, Observable}

/**
 * Created by michelperez on 2/25/15.
 */
object SubscriptionsExamples extends App {


  val b = Subscription {
    print("eooo")
  }

  val a = Subscription {
    print("eooo")
  }

  //val c = CompositeSubscription(a, b)
  //
  //c.unsubscribe()

  val multi = MultipleAssignmentSubscription()

  multi.subscription = b

  multi.unsubscribe()

  multi.subscription = a

}
