package week10

import scalaz._
import Scalaz._

/**
 * Created by michelperez on 4/22/15.
 */
object ScalaZ8 extends App {
  //RPN Calculator
  def foldingFunction(list: List[Double], next: String): Option[List[Double]] = (list, next) match {
    case (x :: y :: ys, "*") => ((y * x) :: ys).point[Option]
    case (x :: y :: ys, "+") => ((y + x) :: ys).point[Option]
    case (x :: y :: ys, "-") => ((y - x) :: ys).point[Option]
    case (xs, numString) => numString.parseInt.toOption map {
      _ :: xs
    }
  }

  def solveRPN(s: String): Option[Double] = for {
    List(x) <- s.split(' ').toList.foldLeftM[Option, List[Double]](Nil: List[Double]) {
      foldingFunction
    }
  } yield x

  solveRPN("a 10 4 3 + 2 * - +").println

  //  List(1, 2, 3).foldRight(0) { (x, y) => s"$x + $y".println; x + y }.println

  // composing functions

  val f = Kleisli.apply[Option, Int, Int] { (x) => (x + 1).some }
  val g = Kleisli.apply[Option, Int, Int] { (x) => (x * 100).some }
  (4.some >>= (f >=> g)).println
  (4.some >>= (f <=< g)).println
}
