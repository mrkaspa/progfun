package mathops

/**
 * Created by michelperez on 2/14/15.
 */
object Sums extends App {

  //recursive function taking another funcion as a parameter
  def sum(a: Int, b: Int, f: (Int) => Int): Int = {
    if (a > b) 0 else f(a) + (sum(a + 1, b, f))
  }

  def sumBlock(a: Int, b: Int)(f: (Int) => Int): Int = {
    if (a > b) 0
    else f(a) + sumBlock(a + 1, b) {
      f
    }
  }

  def nothing(x: Int) = x

  def pow2(x: Int) = x * x

  printf(s"${sum(1, 3, nothing)}")

  printf(s"${
    sumBlock(1, 3) {
      nothing
    }
  }")

  printf(s"${
    sumBlock(1, 3) { x =>
      x * x * x
    }
  }")

  //recursive function using tail recursion
  def sumTail(f: Int => Int, a: Int, b: Int): Int = {
    def loop(a: Int, acc: Int): Int = {
      if (a > b) acc
      else loop(a + 1, f(a) + acc)
    }
    loop(a, 0)
  }

  printf(s"SUM TAIL\n${sumTail(x => x, 1, 5)}")

  //function returning another function
  def sumGen(f: Int => Int): (Int, Int) => Int = {
    def sumRec(a: Int, b: Int): Int = {
      if (a > b) 0 else f(a) + sumRec(a + 1, b)
    }
    sumRec
  }

  def sumCurr(f: Int => Int)(a: Int, b: Int): Int = {
    if (a > b) 0 else f(a) + sumCurr(f)(a + 1, b)
  }

  val sumCube = sumCurr(x => x * x * x)(_, _)
  printf(s"\n${sumCube(1, 3)}")

}
