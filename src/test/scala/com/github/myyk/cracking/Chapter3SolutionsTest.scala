package com.github.myyk.cracking

import com.github.myyk.cracking.Chapter3Solutions.FullStackException
import org.scalatest.{FlatSpec, Matchers}

import java.util
import java.util.EmptyStackException
import scala.collection.JavaConverters._
import scala.util.Random

class Chapter3SolutionsTest extends FlatSpec with Matchers {

  // should be empty to start with
  def testStandardStackBehavior(stack: util.Stack[Integer]): Unit = {
    stack.isEmpty shouldBe true
    intercept[EmptyStackException] {
      stack.peek()
    }
    for (i <- 1 to 10) {
      stack.push(i)
    }
    for (i <- 10 to 1 by -1) {
      stack.pop() shouldBe i
    }
    stack.isEmpty shouldBe true
  }

  // should be empty to start with
  def testStandardQueueBehavior(queue: util.Queue[Integer]): Unit = {
    queue.isEmpty shouldBe true
    queue.poll() shouldBe null
    intercept[NoSuchElementException] {
      queue.remove()
    }
    val randomNumbers = for (_ <- 1 to 10) yield {
      Random.nextInt()
    }
    for (i <- randomNumbers) {
      queue.add(i)
    }
    for (i <- randomNumbers.take(5)) {
      queue.poll() shouldBe i
    }
    for (i <- randomNumbers) {
      queue.add(i)
    }
    for (i <- randomNumbers.takeRight(5)) {
      queue.poll() shouldBe i
    }
    for (i <- randomNumbers) {
      queue.poll() shouldBe i
    }
    queue.isEmpty shouldBe true
    for (i <- randomNumbers) {
      queue.add(i)
    }
    val (oddIndex, evenIndex) = randomNumbers.zipWithIndex.partition(_._2%2==1)
    queue.removeAll(oddIndex.map(_._1).asJava)
    for (i <- evenIndex.map(_._1)) {
      queue.poll() shouldBe i
    }
    queue.isEmpty shouldBe true
  }

  "testStandardStackBehavior" should "work with the standard library" in {
    testStandardStackBehavior(new util.Stack[Integer]())
  }

  "testStandardQueueBehavior" should "work with the standard library" in {
    testStandardQueueBehavior(new util.LinkedList[Integer]())
  }

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

  "StackWithMin" should "be a stack that can always provide the min" in {
    val stack = new Chapter3Solutions.StackWithMin[Integer]()
    testStandardStackBehavior(stack)

    for (i <- 10 to 1 by -1) {
      stack.push(i)
      stack.min shouldBe i
    }
    for (i <- 1 to 10) {
      stack.min shouldBe i
      stack.pop() shouldBe i
    }
    stack.isEmpty shouldBe true
  }

  "SetOfStacks" should "be a stack that can popAt a sub-stack" in {
    val stack = new Chapter3Solutions.SetOfStacks[Integer](3)
    testStandardStackBehavior(stack)

    for (i <- 1 to 10) {
      stack.push(i)
    }
    stack.popAt(3) shouldBe 10
    intercept[IndexOutOfBoundsException] {
      stack.popAt(3)
    }
    stack.popAt(0) shouldBe 3
    stack.popAt(1) shouldBe 6
    stack.popAt(2) shouldBe 9
    stack.popAt(0) shouldBe 2
    stack.popAt(1) shouldBe 5
    stack.popAt(2) shouldBe 8
    stack.popAt(0) shouldBe 1
    intercept[IndexOutOfBoundsException] {
      stack.popAt(2)
    }
    stack.popAt(0) shouldBe 4
    stack.isEmpty shouldBe false
    stack.popAt(0) shouldBe 7
    stack.isEmpty shouldBe true
  }

  "QueueFromStacks" should "be a Queue" in {
    val queue = new Chapter3Solutions.QueueFromStacks[Integer]()
    testStandardQueueBehavior(queue)
  }

  "sortStack" should "sort the stack" in {
    val stack = new util.Stack[Integer]()
    val randomNumbers = for (_ <- 1 to 10) yield {
      Random.nextInt()
    }
    for (i <- randomNumbers) {
      stack.push(i)
    }
    Chapter3Solutions.sortStack(stack)
    stack.asScala.toList shouldBe randomNumbers.toList.sorted
  }
}