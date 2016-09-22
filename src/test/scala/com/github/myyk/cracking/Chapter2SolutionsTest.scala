package com.github.myyk.cracking

import org.scalatest.FlatSpec
import org.scalatest.Matchers

import Chapter2Solutions.Node

class Chapter2SolutionsTest extends FlatSpec with Matchers {
  def toNodes(iterable: Seq[Int]): Node = {
    iterable.reverse.foldLeft(null: Node)((next, i) => new Node(i, next))
  }

  def toSeq(n: Node): Seq[Int] = {
    val builder = Seq.newBuilder[Int]
    var prev = n
    while (prev != null) {
      builder += prev.value
      prev = prev.next
    }
    builder.result
  }

  def test(input: Seq[Int], expected: Seq[Int], testOp: Node => Node): Unit = {
    toSeq(testOp(toNodes(input))) shouldBe expected
  }

  "removeDups" should "return whether or not a string has all unique characters" in {
    test(Nil, Nil, Chapter2Solutions.removeDups)
    test(List(1,2,3,4), List(1,2,3,4), Chapter2Solutions.removeDups)
    test(List(1,1,2,1,1,3,4,1,1), List(1,2,3,4), Chapter2Solutions.removeDups)
  }

}