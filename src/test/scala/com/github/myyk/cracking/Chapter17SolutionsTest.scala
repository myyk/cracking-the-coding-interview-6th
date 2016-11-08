package com.github.myyk.cracking

import org.scalatest.Matchers
import org.scalatest.FlatSpec
import scala.util.Random
import com.github.myyk.cracking.Chapter17Solutions._

class Chapter17SolutionsTest extends FlatSpec with Matchers {
  "addWithoutPlus" should "add two integers" in {
    def testAddWithoutPlus(a: Int, b: Int): Unit = {
      addWithoutPlus(a, b) shouldBe (a + b)
      addWithoutPlus(b, a) shouldBe (a + b)
    }

    testAddWithoutPlus(0, 0)
    testAddWithoutPlus(1, 0)
    testAddWithoutPlus(-1, 0)
    testAddWithoutPlus(-1, -1)
    testAddWithoutPlus(-1, 1)
    testAddWithoutPlus(1, 1)

    for (i <- 1 to 100) {
      testAddWithoutPlus(Random.nextInt(), Random.nextInt())
    }
  }

  "shuffle" should "shuffle a deck with equal probablities" in {
    val deck: Array[Integer] = (0 to 51).map(new Integer(_)).toArray
    shuffle(deck)
//    println(deck.mkString(","))
  }

  "randomSet" should "create a random set from an array of values" in {
    randomSet((0 to 51).toArray, 10) should have size 10
  }

  "missingNumber" should "find the missing number in the array" in {
    missingNumber(Array.emptyIntArray) shouldBe 0
    missingNumber(Array(0)) shouldBe 1
    missingNumber(Array(1)) shouldBe 0
    for (i <- 0 to 3) {
      missingNumber((0 to 3).filter(_ != i).toArray) shouldBe i
    }
    for (i <- 0 to 4) {
      missingNumber((0 to 4).filter(_ != i).toArray) shouldBe i
    }

    for (i <- 12 to 100) {
      missingNumber((0 to i).filter(_ != 10).toArray) shouldBe 10
    }
  }

  "findLongestSubArrayWithEqualLettersAndNumbers" should "find longest subarray with equal letters and numbers" in {
    findLongestSubArrayWithEqualLettersAndNumbers(Array.emptyCharArray) shouldBe Array.emptyCharArray
    findLongestSubArrayWithEqualLettersAndNumbers(Array('a')) shouldBe Array.emptyCharArray
    findLongestSubArrayWithEqualLettersAndNumbers(Array('1')) shouldBe Array.emptyCharArray
    findLongestSubArrayWithEqualLettersAndNumbers(Array('1', 'a')) shouldBe Array('1', 'a')
    Set(Array('a', '1'), Array('1', 'b')) should contain (findLongestSubArrayWithEqualLettersAndNumbers(Array('a', '1', 'b', 'c')))
    Set(Array('1', 'a'), Array('2', '3')) should contain (findLongestSubArrayWithEqualLettersAndNumbers(Array('1', 'a', '2', '3')))
    Set(Array('z', '1', 'a', '2', '3', 'b'), Array('1', 'a', '2', '3', 'b', 'c')) should contain (findLongestSubArrayWithEqualLettersAndNumbers(Array('z', '1', 'a', '2', '3', 'b', 'c', 'z')))
  }

  def countsOfTwoBruteForce(n: Int): Int = {
    if (n <= 0) {
      0
    } else {
      (0 to n).map(_.toString).view.flatten.count(_ == '2')
    }
  }

  def testCountsOfTwo(n: Int): Unit = {
    countsOfTwo(n) shouldBe countsOfTwoBruteForce(n)
  }

  "countsOfTwo" should "find the number of 2s in all the numbers 0 to n" in {
    countsOfTwoBruteForce(25) shouldBe 9
    testCountsOfTwo(25)
    testCountsOfTwo(125)
    testCountsOfTwo(325)
    testCountsOfTwo(1125)
    testCountsOfTwo(12314)
    testCountsOfTwo(22314)
    testCountsOfTwo(52314)
  }
}