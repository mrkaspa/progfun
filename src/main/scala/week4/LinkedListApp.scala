package week4

import week3.{Empty, NonEmpty}
import math.Ordering
/**
 * Created by michelperez on 2/18/15.
 */

trait LinkedList[+A] {
  def isEmpty: Boolean

  def head: A

  def tail: LinkedList[A]

  def at(index: Int): A

  def prepend[B >: A](elem: B): LinkedList[B] = new Cons(elem, this)
}

object Nil extends LinkedList[Nothing] {
  override def isEmpty: Boolean = true

  override def tail: Nothing = throw new NoSuchElementException("Nil.tail")

  override def head: Nothing = throw new NoSuchElementException("Nil.head")

  override def at(index: Int): Nothing = throw new IndexOutOfBoundsException
}

class Cons[A](val head: A, val tail: LinkedList[A]) extends LinkedList[A] {
  override def isEmpty: Boolean = false

  def at(index: Int): A = {
    if (index == 0) head else tail.at(index - 1)
  }
}

object LinkedListApp extends App {
  val list = new Cons(1, new Cons(2, new Cons(3, Nil)))
  println(list at 1)

  def f(xs: LinkedList[NonEmpty[Int]], x: Empty[Int]) = xs.prepend(x)

  println(f(new Cons[NonEmpty[Int]](new NonEmpty[Int](3, new Empty[Int], new Empty[Int]), Nil), new Empty[Int]).getClass )
}
