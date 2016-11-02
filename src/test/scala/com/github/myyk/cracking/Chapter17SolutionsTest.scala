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
}