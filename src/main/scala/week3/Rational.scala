package week3

/**
 * Created by michelperez on 2/16/15.
 */
case class Rational(x: Int, y: Int) {
  require(y!=0, "Denominator can't be 0")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  private val g = gcd(x, y)
  def numer = x / g
  def denom = y / g

  def < (o: Rational) = numer * o.denom < o.numer * denom

  def > (o: Rational) = !(this < o)

  def +(o: Rational) = {
    new Rational(numer * o.denom + denom * o.numer, denom * o.denom)
  }

  def -(o: Rational) = {
    this + (-o)
  }

  def unary_- = Rational(-numer, denom)

  override def toString = {
    numer + "/" + denom
  }

}


object Main extends App {
  println(-(Rational(3, 2) - Rational(1, 2)))
}