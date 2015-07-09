package week10

/**
 * Created by michelperez on 7/9/15.
 */

object ScalaZPLus extends App{

  trait Monoid[A] {
    def mappend(a1: A, a2: A): A

    def mzero: A
  }

  trait MonoidOp[A] {
    val F: Monoid[A]
    val value: A

    def |+|(a2: A) = F.mappend(value, a2)
  }

  implicit val plusInt = new Monoid[Int] {

    override def mappend(a1: Int, a2: Int): Int = a1 + a2

    override def mzero: Int = 1
  }

  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    override val value: A = a
  }

  val list = List(1,2,3,4,5)

  println(list.foldLeft(0) { _ |+| _ })

}
