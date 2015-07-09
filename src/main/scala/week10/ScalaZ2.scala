package week10

import scalaz._
import scalaz.Scalaz._
import scalaz.syntax.FunctorOps
//import scalaz.syntax.EqualOps

/**
 * Created by michelperez on 4/17/15.
 */
object ScalaZ2 extends App {

  case class Box[A](itemType: A)

  implicit val eqBox = Equal.equal[Box[_]]{
    case (Box(a), Box(b)) => a == b
  }

  import eqBox.equalSyntax._

  implicit val machFunctor = new Functor[Box] {
    override def map[A, B](fa: Box[A])(f: (A) => B): Box[B] = {
      val b = f(fa.itemType)
      Box(b)
    }
  }

  implicit val showBox = Show.show[Box[_]]{
    case Box(a) => s"----${a.toString}----"
  }

  import machFunctor.functorSyntax._

  val box1 = Box(1)

  println(machFunctor.map(box1) { x => s"$x..." })
  box1 map { x => s"$x..." }

  implicit val appl = new Applicative[Box] {

    override def point[A](a: => A): Box[A] = Box(a)

    override def ap[A, B](fa: => Box[A])(f: => Box[(A) => B]): Box[B] =
      Box(f.itemType.apply(fa.itemType))
  }

  import appl.applicativeSyntax._

  val box123: Box[Int] = appl.point(10)

  val box345: Box[Int] = 1.point

  val box9 = ^(box123, box345) {_+_}

  val box10 = (box123 |@| box345) {_+_}

  box123 === box345

  import showBox.showSyntax._

  box9.println

  box9 |> showBox.show

  println(s"box9 => $box9")

}