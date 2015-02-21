package week3

/**
 * Created by michelperez on 2/17/15.
 */
abstract class IntSet[A] {
  def incl(x: A): IntSet[A]

  def contains(x: A): Boolean

  def union(intSet: IntSet[A]):IntSet[A]
}

class Empty[A] extends IntSet[A] {

  override def incl(x: A): IntSet[A] = new NonEmpty(x, new Empty, new Empty)

  override def contains(x: A): Boolean = false

  override def union(intSet: IntSet[A]): IntSet[A] = intSet

  override def toString = "."

}

class NonEmpty[A](value: A, left: IntSet[A], right: IntSet[A]) extends IntSet[A] {

  override def incl(x: A): IntSet[A] = {
    x.asInstanceOf[Comparable[A]].compareTo(value) match {
      case 1 => new NonEmpty[A](value, left, right.incl(x))
      case -1 => new NonEmpty[A](value, left.incl(x), right)
      case 0 => this
    }
  }

  override def contains(x: A): Boolean = {
    x.asInstanceOf[Comparable[A]].compareTo(value) match {
      case 1 => right.contains(x)
      case -1 => left.contains(x)
      case 0 => true
    }
  }

  override def union(intSet: IntSet[A]): IntSet[A] = ((left union right) union intSet) incl value

  override def toString = "{" + left + value + right + "}"

}

object BTree extends App {
  val t1 = new NonEmpty[Int](3, new Empty[Int], new Empty[Int])
  println(t1)
  val t2 = t1 incl 4
  println(t2)
  val t3 = t2 incl 1
  println(t3)

  val t4 = new NonEmpty[Int](10, new Empty[Int], new Empty[Int])
  println(t4)
  val t5 = t4 incl 9
  println(t5)

  val t6 = t3 union t5
  println(t6)
}
