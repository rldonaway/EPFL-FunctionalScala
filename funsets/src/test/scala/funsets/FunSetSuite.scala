package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val evens = (x: Int) => x%2 == 0
    val odds = (x: Int) => x%2 == 1
    val threes = (x: Int) => x%3 == 0
    val empty = (x: Int) => false
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      printSet(s)
      assert(contains(s, 1), "Union should contain 1")
      assert(contains(s, 2), "Union should also contain 2")
      assert(!contains(s, 3), "Union should not contain 3")
      val sue = union(s1, empty)
      printSet(sue)
      assert(contains(sue, 1), "Union with empty should contain 1")
      assert(!contains(sue, 2), "Union with empty should not contain 2")
      assert(!contains(sue, 3), "Union with empty should not contain 3")
      val twosAndThrees = union(evens, threes)
      assert(contains(twosAndThrees, 0), "Union should contain 0")
      assert(!contains(twosAndThrees, 1), "Union should not contain 1")
      assert(contains(twosAndThrees, 2), "Union should contain 2")
      assert(contains(twosAndThrees, 3), "Union should contain 3")
      assert(contains(twosAndThrees, 4), "Union should contain 4")
      assert(!contains(twosAndThrees, 5), "Union should not contain 5")
      assert(contains(twosAndThrees, 6), "Union should contain 6")
    }
  }

  test("intersection should contain elements in both sets") {
    new TestSets {
      val s = intersect(s1, s2)
      printSet(s)
      assert(!contains(s, 1), "Intersection should not contain 1")
      assert(!contains(s, 2), "Intersection should not contain 2")
      assert(!contains(s, 3), "Intersection should not contain 3")
      val sie = intersect(s1, empty)
      printSet(sie)
      assert(!contains(sie, 1), "Intersection with empty should not contain 1")
      assert(!contains(sie, 2), "Intersection with empty should not contain 2")
      assert(!contains(sie, 3), "Intersection with empty should not contain 3")
      val twosAndThrees = intersect(evens, threes)
      assert(contains(twosAndThrees, 0), "Intersection should contain 0")
      assert(!contains(twosAndThrees, 1), "Intersection should not contain 1")
      assert(!contains(twosAndThrees, 2), "Intersection should not contain 2")
      assert(!contains(twosAndThrees, 3), "Intersection should not contain 3")
      assert(!contains(twosAndThrees, 4), "Intersection should not contain 4")
      assert(!contains(twosAndThrees, 5), "Intersection should not contain 5")
      assert(contains(twosAndThrees, 6), "Intersection should contain 6")
    }
  }

  test("difference should contain elements in the first that are not in the second") {
    new TestSets {
      val s = diff(s1, s2)
      printSet(s)
      assert(contains(s, 1), "Difference should contain 1")
      assert(!contains(s, 2), "Difference should not contain 2")
      assert(!contains(s, 3), "Difference should not contain 3")
      val sc = diff(s2, s1)
      printSet(sc)
      assert(!contains(sc, 1), "Difference should not contain 1")
      assert(contains(sc, 2), "Difference should contain 2")
      assert(!contains(sc, 3), "Difference should not contain 3")
      val sme = diff(s1, empty)
      printSet(sme)
      assert(contains(sme, 1), "Difference with empty should contain 1")
      assert(!contains(sme, 2), "Difference with empty should not contain 2")
      assert(!contains(sme, 3), "Difference with empty should not contain 3")
      val ems = diff(empty, s1)
      printSet(ems)
      assert(!contains(ems, 1), "Difference with empty should not contain 1")
      assert(!contains(ems, 2), "Difference with empty should not contain 2")
      assert(!contains(ems, 3), "Difference with empty should not contain 3")
      val twosMinusThrees = diff(evens, threes)
      assert(!contains(twosMinusThrees, 0), "Difference should not contain 0")
      assert(!contains(twosMinusThrees, 1), "Difference should not contain 1")
      assert(contains(twosMinusThrees, 2), "Difference should contain 2")
      assert(!contains(twosMinusThrees, 3), "Difference should not contain 3")
      assert(contains(twosMinusThrees, 4), "Difference should contain 4")
      assert(!contains(twosMinusThrees, 5), "Difference should not contain 5")
      assert(!contains(twosMinusThrees, 6), "Difference should not contain 6")
      val threesMinusTwos = diff(threes, evens)
      assert(!contains(threesMinusTwos, 0), "Difference should not contain 0")
      assert(!contains(threesMinusTwos, 1), "Difference should not contain 1")
      assert(!contains(threesMinusTwos, 2), "Difference should not contain 2")
      assert(contains(threesMinusTwos, 3), "Difference should contain 3")
      assert(!contains(threesMinusTwos, 4), "Difference should not contain 4")
      assert(!contains(threesMinusTwos, 5), "Difference should not contain 5")
      assert(!contains(threesMinusTwos, 6), "Difference should not contain 6")
    }
  }

  test("filter should only include the elements that have the condition") {
    new TestSets {
      val p = (x: Int) => x%3 == 1
      val s1stp = filter(s1, p)
      assert(contains(s1stp, 1), "Filtered set should contain 1")
      assert(!contains(s1stp, 2), "Filtered set should not contain 2")
      assert(!contains(s1stp, 3), "Filtered set should not contain 3")
      val estp = filter(empty, p)
      assert(!contains(estp, 1), "Filtered empty set should not contain 1")
      assert(!contains(estp, 2), "Filtered empty set should not contain 2")
      assert(!contains(estp, 3), "Filtered empty set should not contain 3")
      val evensstp = filter(evens, p)
      assert(!contains(evensstp, 0), "Filtered set should not contain 0")
      assert(!contains(evensstp, 1), "Filtered set should not contain 1")
      assert(!contains(evensstp, 2), "Filtered set should not contain 2")
      assert(!contains(evensstp, 3), "Filtered set should not contain 3")
      assert(contains(evensstp, 4), "Filtered set should contain 4")
      assert(!contains(evensstp, 5), "Filtered set should not contain 5")
      assert(!contains(evensstp, 6), "Filtered set should not contain 6")
    }
  }

  test("forall should return true only if the condition holds for every element in the given set") {
    new TestSets {
      val p = (x: Int) => x%3 == 1
      assert(forall(s1, p), "every element of s1 has p")
      assert(!forall(s2, p), "not every element of s2 has p")
      assert(!forall(s3, p), "not every element of s3 has p")
      assert(forall(empty, p), "not every element of the empty set has p")
      assert(!forall(evens, p), "not every element of the evens has p")
      assert(!forall(odds, p), "not every element of the odds has p")
      assert(!forall(threes, p), "not every element of the multiples of three has p")
    }
  }

  test("exists should return true when the condition holds for at least one element in the given set") {
    new TestSets {
      val p = (x: Int) => x%3 == 1
      assert(exists(s1, p), "an element of s1 has p")
      assert(!exists(s2, p), "no element of s2 has p")
      assert(!exists(s3, p), "no element of s3 has p")
      assert(!exists(empty, p), "no element of the empty set has p")
      assert(exists(evens, p), "an element of the evens has p")
      assert(exists(odds, p), "an element of the odds has p")
      assert(!exists(threes, p), "no element of the multiples of three has p")
    }
  }

}
