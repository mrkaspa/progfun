package week1

/**
 * Created by michelperez on 2/13/15.
 */
object SquareRoot extends App {

  def abs(x: Double) = if (x < 0) -x else x

  def sqrt(x: Double) = {

    def isGoodEnough(guess: Double): Boolean = abs(guess * guess - x) / x < 0.001

    def improve(guess: Double): Double = (guess + x / guess) / 2

    def sqrtIter(guess: Double): Double = {
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess))
    }

    sqrtIter(1)
  }

  println(s" >> ${sqrt(4)}")

}
