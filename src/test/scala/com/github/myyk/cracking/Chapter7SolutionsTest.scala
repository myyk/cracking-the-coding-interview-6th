package com.github.myyk.cracking

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class Chapter7SolutionsTest extends FlatSpec with Matchers {
  def testTripleStep(tripleStep: Int => BigInt): Unit = {
    tripleStep(0) shouldBe 1
    tripleStep(1) shouldBe 1
    tripleStep(2) shouldBe 2
    tripleStep(3) shouldBe 4
    tripleStep(4) shouldBe 7
    tripleStep(5) shouldBe 13
  }

  "tripleStepRecursive" should "count the ways the child can go up the stairs with 1, 2, or 3 steps at a time" in {
    testTripleStep(Chapter7Solutions.tripleStepRecursive)
  }
  "tripleStepIterative" should "count the ways the child can go up the stairs with 1, 2, or 3 steps at a time" in {
    testTripleStep(Chapter7Solutions.tripleStepIterative)
  }
}