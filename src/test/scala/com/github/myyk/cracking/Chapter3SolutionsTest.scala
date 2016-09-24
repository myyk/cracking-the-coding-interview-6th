package com.github.myyk.cracking
import org.scalatest.FlatSpec
import org.scalatest.Matchers
import java.util.EmptyStackException
import com.github.myyk.cracking.Chapter3Solutions.FullStackException

class Chapter3SolutionsTest extends FlatSpec with Matchers {
  
  "arrayStack" should "be a single array implementing 3 stacks" in {
    val stack = new Chapter3Solutions.FixedMultiStack[Int](30)
    intercept[IllegalArgumentException] {
      stack.peek(-1)
    }
    intercept[IllegalArgumentException] {
      stack.peek(4)
    }
    intercept[EmptyStackException] {
      stack.peek(0)
    }
    stack.push(10, 1)
    stack.peek(1) shouldBe 10
    stack.pop(1) shouldBe 10

    stack.push(11, 1)
    for (i <- 1 to 10) {
      stack.push(i, 0)
    }
    intercept[FullStackException] {
      stack.push(99, 0)
    }
    stack.pop(1) shouldBe 11
    stack.pop(0) shouldBe 10

    for (i <- 1 to 10) {
      stack.push(i, 2)
    }
    intercept[FullStackException] {
      stack.push(99, 2)
    }
    for (i <- 10 to 1 by -1) {
      stack.pop(2) shouldBe i
    }
    intercept[EmptyStackException] {
      stack.pop(2)
    }
  }
}