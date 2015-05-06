package week2

import scala.annotation.tailrec

/**
 * Created by michelperez on 3/13/15.
 */
object RecursiveSum extends App {
  println(sum(List(1, 2, 3, 10)))

  def sum(nums: List[Int]): Int = {
    @tailrec
    def sumTail(x: Int, xs: List[Int]): Int = xs match {
      case Nil => x
      case y :: ys => sumTail(x + y, ys)
    }
    sumTail(0, nums)
  }
}
