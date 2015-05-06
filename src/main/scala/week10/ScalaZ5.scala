package week10

import scalaz._
import Scalaz._

/**
 * Created by michelperez on 4/20/15.
 */
object ScalaZ5 extends App {

  def justH: Option[Char] =
    for {
      (x :: xs) <- "hello".toList.some
    } yield x

  def wopwop: Option[Char] =
    for {
      (x :: xs) <- "".toList.some
    } yield x

  List(1, 2, 3) <+> List(4, 5, 6)

  List(1, 2, 3) ++ List(4, 5, 6)

  //List(1, 2, 3) <+> List("1") doesn't allow different types

  // this does it
  List(1, 2, 3) ++ List("1")

  //here began the kings quest >>
  case class KnightPos(c: Int, r: Int) {
    def move: List[KnightPos] =
      for {
        KnightPos(c2, r2) <- List(KnightPos(c + 2, r - 1),
          KnightPos(c + 2, r + 1), KnightPos(c - 2, r - 1),
          KnightPos(c - 2, r + 1), KnightPos(c - 1, r + 2),
          KnightPos(c + 1, r - 2), KnightPos(c + 1, r + 2),
          KnightPos(c - 1, r - 2))
        if ((1 |-> 8) contains c2) && ((1 |-> 8) contains r2)
      } yield KnightPos(c2, r2)

    def move3: List[KnightPos] =
      for {
        first <- move
        second <- first.move
        third <- second.move
      } yield third

    def canReach(end: KnightPos): Boolean = move3 contains end
  }

  println(s"moves >> ${KnightPos(6, 2).move}")

}
