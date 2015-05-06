package testk

import org.scalatest.{WordSpec, GivenWhenThen, FunSpec, FunSuite}

/**
 * Created by michelperez on 4/21/15.
 */
class SetSuite extends WordSpec with GivenWhenThen{

  "Empty set" should  {
    "be zero" ignore {
      When("The set is declared")
      assert(Set.empty.size == 0)
    }
  }

}
