package mathops

/**
 * Created by michelperez on 2/14/15.
 */
object Prods extends App {

  def prodCurr(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 1 else f(a) * (prodCurr(f)(a + 1, b))
  }

  def fact(n: Int) = prodCurr(x => x)(1, n)

  printf(s"\nFACT >> ${fact(5)}")

  def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int): Int = {
    if (a > b) zero else combine(f(a), mapReduce(f, combine, zero)(a + 1, b))
  }

  def prodReduce(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x: Int, y: Int) => x * y, 1)(a, b)

  def factReduce(n: Int) = prodReduce(x => x)(1, n)

  printf(s"\nFACT REDUCE >> ${factReduce(5)}")

}
