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
}