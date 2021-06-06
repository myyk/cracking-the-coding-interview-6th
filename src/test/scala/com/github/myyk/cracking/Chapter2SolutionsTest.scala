package com.github.myyk.cracking

import Chapter2Solutions.Node

import org.scalatest._
import flatspec._
import matchers._

class Chapter2SolutionsTest extends AnyFlatSpec with should.Matchers {
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
    builder.result()
  }

  def test(input: Seq[Int], expected: Seq[Int], testOp: Node => Node): Unit = {
    toSeq(testOp(toNodes(input))) shouldBe expected
  }

  "removeDups" should "return whether or not a string has all unique characters" in {
    test(Nil, Nil, Chapter2Solutions.removeDups)
    test(List(1, 2, 3, 4), List(1, 2, 3, 4), Chapter2Solutions.removeDups)
    test(
      List(1, 1, 2, 1, 1, 3, 4, 1, 1),
      List(1, 2, 3, 4),
      Chapter2Solutions.removeDups
    )
    test(
      List(1, 2, 3, 2, 4, 2, 2),
      List(1, 2, 3, 4),
      Chapter2Solutions.removeDups
    )
  }

  def testKToLast(k: Int, input: Seq[Int], expected: Seq[Int]): Unit = {
    toSeq(Chapter2Solutions.kToLast(k, toNodes(input))) shouldBe expected
  }

  "kToLast" should "return the k-th node" in {
    testKToLast(1, Nil, Nil)
    testKToLast(-1, List(1, 2, 3), Nil)
    testKToLast(0, List(1, 2, 3), List(3))
    testKToLast(1, List(1, 2, 3), List(2, 3))
    testKToLast(2, List(1, 2, 3), List(1, 2, 3))
    testKToLast(3, List(1, 2, 3), Nil)
  }

  "deleteMiddleNode" should "delete the provided middle node" in {
    val list1 = toNodes(List(1, 2, 3, 4, 5))
    Chapter2Solutions.deleteMiddleNode(list1.next.next)
    toSeq(list1) shouldBe List(1, 2, 4, 5)

    val list2 = toNodes(List(1, 2, 3, 4, 5))
    Chapter2Solutions.deleteMiddleNode(list2)
    toSeq(list2) shouldBe List(2, 3, 4, 5)
  }

  def testPartition(x: Int, list: List[Int]): Unit = {
    val result = toSeq(Chapter2Solutions.partition(x, toNodes(list)))
    // partitioning again shouldn't change order
    val (front, back) = result.partition(_ < x)
    result shouldBe front ++ back
  }

  "partition" should "partition the list around x" in {
    testPartition(5, List(3, 5, 8, 5, 10, 2, 1))
    testPartition(5, List(6, 3, 5, 8, 5, 10, 2, 1))
    testPartition(5, List())
    testPartition(5, List(5))
    testPartition(5, List(1))
  }

  def testSumLists1(a: Int, b: Int): Unit = {
    def toIntSeq(n: Int): Seq[Int] = {
      n.toString.reverse.map(_.asDigit).toList
    }
    def toNode(n: Int): Node = {
      toNodes(toIntSeq(n))
    }
    toSeq(Chapter2Solutions.sumLists1(toNode(a), toNode(b))) shouldBe toIntSeq(
      a + b
    )
  }

  def testSumLists2(a: Int, b: Int): Unit = {
    def toIntSeq(n: Int): Seq[Int] = {
      n.toString.map(_.asDigit).toList
    }
    def toNode(n: Int): Node = {
      toNodes(toIntSeq(n))
    }
    toSeq(Chapter2Solutions.sumLists2(toNode(a), toNode(b))) shouldBe toIntSeq(
      a + b
    )
  }

  "sumList1" should "sum lists of digits with 1s magnitude first" in {
    testSumLists1(1, 1)
    testSumLists1(131234, 9723464)
    testSumLists1(99999, 1)
    testSumLists1(1, 99999)
  }

  "sumLists2" should "sum lists of digits with 1s magnitude last" in {
    testSumLists2(1, 1)
    testSumLists2(131234, 9723464)
    testSumLists2(99999, 1)
    testSumLists2(1, 99999)
  }

  def testIsPalindrome(list: Seq[Int], expected: Boolean): Unit = {
    Chapter2Solutions.isPalindrome(toNodes(list)) shouldBe expected

  }

  "isPalindrome" should "determine if a link list contains a palindrome" in {
    testIsPalindrome(List(), expected = true)
    testIsPalindrome(List(1), expected = true)
    testIsPalindrome(List(8, 9), expected = false)
    testIsPalindrome(List(1, 2, 3), expected = false)
    testIsPalindrome(List(2, 1, 3, 3, 1, 2), expected = true)
    testIsPalindrome(List(2, 1, 3, 1, 2), expected = true)
  }

  def testFindIntersection(intersection: Seq[Int]): Unit = {
    val n = toNodes(intersection)
    val list1 = new Node(1, n)
    val list2 = new Node(2, n)
    Chapter2Solutions.findIntersection(list1, list2) shouldBe n
    Chapter2Solutions.findIntersection(list1, n) shouldBe n
    Chapter2Solutions.findIntersection(n, list2) shouldBe n
    Chapter2Solutions.findIntersection(n, n) shouldBe n
  }

  "findIntersection" should "find the intersection of two linked lists" in {
    testFindIntersection(List(1, 2, 3, 4))
    testFindIntersection(List(5))
  }

  "findLoopStart" should "find the start of a loop if there is one in the linked list" in {
    Chapter2Solutions.findLoopStart(new Node(1, null)) shouldBe null

    val n1 = toNodes(List(1, 2, 3, 4, 5))
    n1.next.next.next.next.next = n1.next.next //create the loop 5->3
    Chapter2Solutions.findLoopStart(n1).value shouldBe n1.next.next.value

    val n2 = toNodes(List(1, 2, 3))
    n2.next.next.next = n2.next //create the loop 3->2
    Chapter2Solutions.findLoopStart(n2).value shouldBe n2.next.value
  }
}
