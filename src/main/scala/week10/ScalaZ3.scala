package week10

import scalaz._
import Scalaz._

/**
 * Created by michelperez on 4/18/15.
 */
object ScalaZ3 extends App {

  sealed trait KiloGram

  def KiloGram[A](a: A): A @@ KiloGram = Tag[A, KiloGram](a)

  val mass = KiloGram(20.0)

  val mass2 = 2 * Tag.unsubst[Double, Id, KiloGram](mass)

  println(s"mass2 >> $mass2")

  sealed trait JoulePerKiloGram

  def JoulePerKiloGram[A](a: A): A @@ JoulePerKiloGram = Tag[A, JoulePerKiloGram](a)

  def energyR(m: Double @@ KiloGram): Double @@ JoulePerKiloGram =
    JoulePerKiloGram(299792458.0 * 299792458.0 * Tag.unsubst[Double, Id, KiloGram](m))

  println(s"energyR >> ${energyR(mass)}")

  List(2.some, 3.some) foldMap {Tags.Max.apply} // get the Min

}
