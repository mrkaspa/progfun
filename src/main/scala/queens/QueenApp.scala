package queens

/**
 * Created by michelperez on 3/3/15.
 */
object QueenApp extends App {

  def queens(n: Int): Set[List[Int]] = {

    def placeQueens(k: Int): Set[List[Int]] = {
      if (k == 0) Set(List())
      else
        for {
          queens <- placeQueens(k - 1)
          col <- 0 until n
          if (isSafe(col, queens))
        } yield col :: queens
    }

    def isSafe(col: Int, queens: List[Int]): Boolean = {
      val row = queens.length
      val queensWithRow = (row - 1 to 0 by -1) zip queens
      queensWithRow forall {
        case (r, c) => col != c && math.abs(col - c) != row - r
      }
    }

    placeQueens(n: Int)
  }

  def showQueens(queens: List[Int]) = {
    val lines =
      for (col <- queens.reverse)
        yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
    "\n" + (lines mkString "\n")
  }

  val t0 = System.nanoTime()
  val solutions = queens(7)

  val t1 = System.nanoTime()

  println("Elapsed time: " + (t1 - t0) / (1000 * 1000) + "ms")
  println("# Solutions = " + solutions.size)

  solutions foreach { queens =>
    println(showQueens(queens))
  }

  val secondElement = List(1,2,3) match {
    case x :: y :: xs => y
    case _ => 0
  }

  val foodItem = "porridge"

  def goldilocks(expr: Any) = expr match {
    case (`foodItem`, _) => "eating"
    case ("chair", "Mama") => "sitting"
    case ("bed", "Baby") => "sleeping"
    case _ => "what?"
  }

  goldilocks(("porridge", "Papa"))

}
