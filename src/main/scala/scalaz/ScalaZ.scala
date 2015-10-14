package scalaz

trait Monoid[A] {
  def mappend(a1: A, a2: A): A

  def mzero: A
}

object Monoid {
  implicit val IntMonoid: Monoid[Int] = new Monoid[Int] {
    def mappend(a: Int, b: Int): Int = a + b

    def mzero: Int = 0
  }
  implicit val StringMonoid: Monoid[String] = new Monoid[String] {
    def mappend(a: String, b: String): String = a + b

    def mzero: String = ""
  }
}

trait FoldLeft[F[_]] {
  def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
}

object FoldLeft {
  implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
    def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
  }

  implicit val FoldLeftSet: FoldLeft[Set] = new FoldLeft[Set] {
    def foldLeft[A, B](xs: Set[A], b: B, f: (B, A) => B) = xs.foldLeft(b)(f)
  }
}

trait MonoidOp[A] {
  val F: Monoid[A]
  val value: A

  def |+|(a2: A) = F.mappend(value, a2)
}

object MonoidOp {
  implicit def toMonoidOp[A: Monoid](a: A): MonoidOp[A] = new MonoidOp[A] {
    val F = implicitly[Monoid[A]]
    override val value: A = a
  }
}

object Main extends App {

  import Monoid._
  import FoldLeft._
  import MonoidOp._

  def sum1[A](xs: List[A])(implicit m: Monoid[A]): A = xs.foldLeft(m.mzero)(m.mappend)

  def sum2[A: Monoid](xs: List[A]): A = {
    val m = implicitly[Monoid[A]]
    xs.foldLeft(m.mzero)(m.mappend)
  }

  def sum3[M[_] : FoldLeft, A: Monoid](xs: M[A]): A = {
    val m = implicitly[Monoid[A]]
    val fl = implicitly[FoldLeft[M]]
    fl.foldLeft(xs, m.mzero, m.mappend)
  }

  def plus[A: Monoid](a: A, b: A): A =
    implicitly[Monoid[A]].mappend(a, b)

  println(sum3(List(1, 2, 3)))

  println(sum3(Set(1, 2, 3)))

  println(plus(1, 2))

  println(1 |+| 2)

  implicit val mk = List("123")

  def demo[A: List]() = {
    println(implicitly[List[A]])
  }

  demo[String]()

}