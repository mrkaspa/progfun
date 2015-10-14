package mathops

import scala.annotation.tailrec

/**
 * Created by michelperez on 4/14/15.
 */
object Fibonacci extends App {

  def fib(nth: Int): Int = {
    @tailrec
    def loop(first: Int, second: Int, iter: Int): Int = {
      val next = first + second
      if (iter == 0)
        next
      else
        loop(second, next, iter - 1)
    }
    nth match {
      case 1 => 0
      case 2 => 1
      case n: Int if n > 0 => loop(0, 1, nth - 3)
    }
  }

  //  println(fib(1))

  def partial1[A, B, C](a: A, f: (A, B) => C): B => C = (b: B) => f(a, b)

  def partial2[A, B, C](a: A, f: (A, B) => C)(b: B): C = f(a, b)

  println(partial1(1, (a: Int, b: Int) => a + b)(2))

  println(partial2(1, (a: Int, b: Int) => a + b)(2))

}
