package primepairs

/**
 * Created by michelperez on 2/20/15.
 */
object PrimePairs extends App {

  def isPrime(n: Int) = 2 until n forall (n % _ != 0)

  val n = 7

  val pairs = (1 until n).flatMap(i =>
    (i until n).map(j =>
      (i, j)
    )
  ) filter { case (x, y) => isPrime(x + y)}

  val forPairs = for {
    i <- 1 until n
    j <- i until n
    if isPrime(i + j)
  } yield (i, j)

  val pairsTranslated = (1 until n).flatMap(i =>
    (1 until i).withFilter(j => isPrime(j + i)).map(j => (i, j)))

  println(forPairs)

}
