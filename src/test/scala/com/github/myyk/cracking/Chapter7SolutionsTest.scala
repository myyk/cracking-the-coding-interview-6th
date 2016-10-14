package com.github.myyk.cracking

import org.scalatest.FlatSpec
import org.scalatest.Matchers

class Chapter7SolutionsTest extends FlatSpec with Matchers {
  "tripleStep" should "count the ways the child can go up the stairs with 1, 2, or 3 steps at a time" in {
    Chapter7Solutions.tripleStep(0) shouldBe 1
    Chapter7Solutions.tripleStep(1) shouldBe 1
    Chapter7Solutions.tripleStep(2) shouldBe 2
    Chapter7Solutions.tripleStep(3) shouldBe 4
  }
  
}