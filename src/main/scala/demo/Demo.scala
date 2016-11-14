package demo

/**
  * Created by michelperez on 11/12/16.
  */

object Demo extends App {
  var ls = List(1, 2, 3, 4)
  val ss = Set(1, 2, 3, 4)

  for {
    num <- ls
    cat <- ss
  } yield {
    println(num)
  }

  val f = (a: String) => {
    println("feo " + a)
  }

  f("demo")

  ls match {
    case hd :: ks =>
      println(s"cabeza $hd")
    case _ =>
      println("nil")
  }

  val list = 1 :: List(45, 66)
  val map = Map("a" -> "b")
  println(list.mkString("..."))

  list match {
    case hd :: _ =>
      println(s"cabeza $hd")
  }
}
