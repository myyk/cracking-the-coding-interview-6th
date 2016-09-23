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
    test(List(1,2,3,2,4,2,2), List(1,2,3,4), Chapter2Solutions.removeDups)
  }

  def testKToLast(k: Int, input: Seq[Int], expected: Seq[Int]): Unit = {
    toSeq(Chapter2Solutions.kToLast(k, toNodes(input))) shouldBe expected
  }

  "kToLast" should "return the k-th node" in {
    testKToLast(1, Nil, Nil)
    testKToLast(-1, List(1,2,3), Nil)
    testKToLast(0, List(1,2,3), List(3))
    testKToLast(1, List(1,2,3), List(2,3))
    testKToLast(2, List(1,2,3), List(1,2,3))
    testKToLast(3, List(1,2,3), Nil)
  }

  "deleteMiddleNode" should "delete the provided middle node" in {
    val list1 = toNodes(List(1,2,3,4,5))
    Chapter2Solutions.deleteMiddleNode(list1.next.next)
    toSeq(list1) shouldBe List(1,2,4,5)

    val list2 = toNodes(List(1,2,3,4,5))
    Chapter2Solutions.deleteMiddleNode(list2)
    toSeq(list2) shouldBe List(2,3,4,5)
  }

  def testPartition(x: Int, list: List[Int]): Unit = {
    val result = toSeq(Chapter2Solutions.partition(x, toNodes(list)))
    // partitioning again shouldn't change order
    val (front, back) = result.partition(_<x)
    result shouldBe front ++ back
  }

  "partition" should "partition the list around x" in {
    testPartition(5, List(3,5,8,5,10,2,1))
    testPartition(5, List())
  }
}