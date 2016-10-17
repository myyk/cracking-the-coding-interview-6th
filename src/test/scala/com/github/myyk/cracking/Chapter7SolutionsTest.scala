package com.github.myyk.cracking

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.github.myyk.cracking.Chapter7Solutions.Robot
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import scala.util.Random
import com.github.myyk.cracking.Chapter7Solutions.Color

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

  def testFindPath(grid: Array[Array[Boolean]]): Unit = {
    val path = Chapter7Solutions.findPath(grid)
    val robot = new Robot(0, 0)
    path should not be (null)
    for (next <- path) {
      if (next) {
        robot.moveRight()
      } else {
        robot.moveDown()
      }

      // check collision
      grid(robot.getY)(robot.getX) shouldBe false
    }
    robot.getX shouldBe grid(0).size - 1
    robot.getY shouldBe grid.size - 1
  }

  def testFindPathNegative(grid: Array[Array[Boolean]]): Unit = {
    Chapter7Solutions.findPath(grid) shouldBe null
  }

  "findPath" should "find a valid path in the grid" in {
    testFindPathNegative(Array(Array(false, true)))
    testFindPathNegative(Array(Array(false, true), Array(true, false)))
    testFindPathNegative(Array(Array(false, false), Array(false, true)))

    testFindPath(Array(Array(true)))
    testFindPath(Array(Array(false)))
    testFindPath(Array(Array(false, false), Array(false, false)))
//    testFindPath(Array(Array(false, true), Array(false, false)))
//    testFindPath(Array(Array(false, false), Array(true, false)))
//    testFindPath(Array(Array(false, true, false), Array(false, false, true), Array(true, false, false)))
  }

  "movesToSolution" should "turn some moves into a solution array" in {
    Chapter7Solutions.movesToSolution(Array(Array(null, true, true))) shouldBe Array(true, true)
    Chapter7Solutions.movesToSolution(Array(Array(null, true, null), Array(null, false, true))) shouldBe Array(true, false, true)
  }

  def testFindMagicIndex(a: Array[Int], op: Array[Int] => Int): Unit = {
    val i = op(a)
    i should be >= 0
    a(i) shouldBe i
  }

  def testFindMagicIndexNotFound(a: Array[Int], op: Array[Int] => Int): Unit = {
    op(a) shouldBe -1
  }

  "findMagicIndexDistinct" should "find a magic index if there's one and elements are distinct" in {
    testFindMagicIndexNotFound(Array(2), Chapter7Solutions.findMagicIndexDistinct) 

    testFindMagicIndex(Array(0), Chapter7Solutions.findMagicIndexDistinct) 
    testFindMagicIndex(Array(-1,0,1,2,3,5), Chapter7Solutions.findMagicIndexDistinct) 
    testFindMagicIndex(Array(-1,0,1,2,4,6), Chapter7Solutions.findMagicIndexDistinct) 
    testFindMagicIndex(Array(0,2,4,6), Chapter7Solutions.findMagicIndexDistinct) 
  }

  "findMagicIndexNonDistinct" should "find a magic index if there's one and elements are distinct" in {
    testFindMagicIndexNotFound(Array(2), Chapter7Solutions.findMagicIndexNonDistinct) 

    testFindMagicIndex(Array(0), Chapter7Solutions.findMagicIndexNonDistinct) 
    testFindMagicIndex(Array(-1,0,1,2,3,5), Chapter7Solutions.findMagicIndexNonDistinct) 
    testFindMagicIndex(Array(-1,0,1,2,4,6), Chapter7Solutions.findMagicIndexNonDistinct) 
    testFindMagicIndex(Array(0,2,4,6), Chapter7Solutions.findMagicIndexNonDistinct) 
    testFindMagicIndex(Array(1,1,2,4,6), Chapter7Solutions.findMagicIndexNonDistinct) 
    testFindMagicIndex(Array(-1,-1,3,4,6,6,6), Chapter7Solutions.findMagicIndexNonDistinct) 
    testFindMagicIndex(Array(-100,-100,-100,-100,-100,-100,6), Chapter7Solutions.findMagicIndexNonDistinct) 
  }

  def testPowerSet[T](input: Set[T], expected: Set[Set[T]]) {
    Chapter7Solutions.powerSet(input).map(_.toSet).toSet shouldBe expected
  }

  "powerSet" should "get the power set of a set" in {
    testPowerSet(Set.empty[Int], Set(Set.empty[Int]))
    testPowerSet(Set(1), Set(Set.empty[Int], Set(1)))
    testPowerSet(Set(1,2), Set(Set.empty[Int], Set(1), Set(2), Set(1,2)))
    testPowerSet(Set(1,2,3), Set(Set.empty[Int], Set(1), Set(2), Set(3), Set(1,2), Set(2,3), Set(1,3), Set(1,2,3)))
  }

  def testMultiply(a: Int, b: Int): Unit = {
    Chapter7Solutions.multiply(a, b) shouldBe (a * b)
  }

  "multiply" should "work like * multiply" in {
    testMultiply(0, 0)
    testMultiply(0, 1)
    testMultiply(1, 0)
    testMultiply(1, 1)
    testMultiply(123, 312)
    testMultiply(123, -1)
    testMultiply(-1, 123)
    testMultiply(-123, -1)
    for (_ <- 1 until 100) {
      testMultiply(Random.nextInt, Random.nextInt)
    }
  }

  "permutationsUnique" should "get all permutations of the string" in {
    Chapter7Solutions.permutationsUnique("abc").toSet shouldBe "abc".permutations.toSet
    Chapter7Solutions.permutationsUnique("myyk").toSet shouldBe "myyk".permutations.toSet
    Chapter7Solutions.permutationsUnique("aaaaa").size shouldBe 1
  }

  "parens" should "get all legal combinations of n open and close parens" in {
    Chapter7Solutions.parens(0).toSet shouldBe Set()
    Chapter7Solutions.parens(1).toSet shouldBe Set("()")
    Chapter7Solutions.parens(2).toSet shouldBe Set("()()", "(())")
    Chapter7Solutions.parens(3).toSet shouldBe Set("()()()", "(())()", "()(())", "((()))", "(()())")
    Chapter7Solutions.parens(4).toSet shouldBe Set("()()()()", "()(())()", "()()(())", "()((()))", "()(()())",
                                                   "(()()())", "((())())", "(()(()))", "(((())))", "((()()))",
                                                   "(())()()", "((()))()", "(()())()")
  }

  "paintFill" should "fill the all the connected old color with the new color" in {
    val newColor = new Color(255.toByte,255.toByte,255.toByte)
    val color1 = new Color(1.toByte,1.toByte,1.toByte)
    val color2 = new Color(2.toByte,2.toByte,2.toByte)
    val image = Array(
        Array(color1, color1, color1, color1, color1),
        Array(color2, color2, color1, color2, color2),
        Array(color1, color1, color1, color1, color1),
        Array(color2, color2, color1, color2, color1),
        Array(color1, color2, color1, color2, color1)
    )
    val expectedImage = Array(
        Array(newColor, newColor, newColor, newColor, newColor),
        Array(color2,   color2,   newColor, color2,   color2),
        Array(newColor, newColor, newColor, newColor, newColor),
        Array(color2,   color2,   newColor, color2,   newColor),
        Array(color1,   color2,   newColor, color2,   newColor)
    )
    Chapter7Solutions.paintFill(image, 2, 2, newColor) shouldBe expectedImage
  }
}