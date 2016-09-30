package com.github.myyk.cracking

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.github.myyk.cracking.Chapter4Solutions.IntGraph
import com.github.myyk.cracking.Chapter4Solutions.IntNode
import com.github.myyk.cracking.Chapter4Solutions.Node
import com.github.myyk.cracking.Chapter4Solutions.Tree
import scala.annotation.tailrec
import scala.util.Random
import scala.collection.JavaConversions._

class Chapter4SolutionsTest extends FlatSpec with Matchers {
  def createMediumDirectionalTestGraph: (IntGraph, Seq[IntNode]) = {
    val graph = new IntGraph()

    val nodes = for (i <- 0 to 7) yield {
      val node = new IntNode(i)
      graph.addNode(node)
      node
    }
    
    nodes(0) addAdjacent nodes(1)
    nodes(0) addAdjacent nodes(2)
    nodes(1) addAdjacent nodes(3)
    nodes(1) addAdjacent nodes(4)
    nodes(2) addAdjacent nodes(5)
    nodes(3) addAdjacent nodes(4)
    nodes(4) addAdjacent nodes(1)
    nodes(4) addAdjacent nodes(6)
    nodes(5) addAdjacent nodes(6)
    nodes(6) addAdjacent nodes(4)

    (graph, nodes)
  }

  def createMediumBidirectionalTestGraph: (IntGraph, Seq[IntNode]) = {
    val graph = new IntGraph()

    val nodes = for (i <- 0 to 7) yield {
      val node = new IntNode(i)
      graph.addNode(node)
      node
    }
    def addEdge(a: Int, b: Int): Unit = {
      nodes(a) addAdjacent nodes(b)
      nodes(b) addAdjacent nodes(a)
    }
    addEdge(0, 1)
    addEdge(0, 2)
    addEdge(1, 3)
    addEdge(1, 4)
    addEdge(2, 5)
    addEdge(3, 4)
    addEdge(4, 6)
    addEdge(4, 6)

    (graph, nodes)
  }

  "pathExists" should "find if there is a path between a and b" in {
    val (graph, nodes) = createMediumDirectionalTestGraph
    Chapter4Solutions.pathExistsDirectional(nodes(0), nodes(0), graph) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes(0), nodes(1), graph) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes(1), nodes(0), graph) shouldBe false
    Chapter4Solutions.pathExistsDirectional(nodes(0), nodes(6), graph) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes(6), nodes(0), graph) shouldBe false
    Chapter4Solutions.pathExistsDirectional(nodes(0), nodes(7), graph) shouldBe false
    Chapter4Solutions.pathExistsDirectional(nodes(6), nodes(1), graph) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes(1), nodes(6), graph) shouldBe true
  }

  "pathExistsBidirectional" should "find if there is a path between a and b" in {
    val (graph, nodes) = createMediumBidirectionalTestGraph
    Chapter4Solutions.pathExistsBidirectional(nodes(0), nodes(0)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(0), nodes(1)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(1), nodes(0)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(0), nodes(6)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(6), nodes(0)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(6), nodes(1)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(1), nodes(6)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(0), nodes(7)) shouldBe false
    Chapter4Solutions.pathExistsBidirectional(nodes(7), nodes(0)) shouldBe false
  }

  def getHeight(tree: Tree[_]): Int = {
    if (tree == null) {
      0
    } else {
      1 + Math.max(getHeight(tree.getLeft), getHeight(tree.getRight))
    }
  }

  @tailrec
  private def binarySearch(value: Int, tree: Tree[Integer]): Boolean = {
    if (tree == null) {
      false
    } else if (value == tree.getData) {
      true
    } else if (value < tree.getData) {
      binarySearch(value, tree.getLeft)
    } else {
      binarySearch(value, tree.getRight)
    }
  }

  def log2(num: Int): Int = {
    if (num == 0) {
      0
    } else {
      31 - Integer.numberOfLeadingZeros(num)
    }
  }

  def testMinBinaryTree(array: Array[Int]): Unit = {
    val bst = Chapter4Solutions.minBinaryTree(array)
    if (array.isEmpty) {
      getHeight(bst) shouldBe 0
    } else {
      getHeight(bst) shouldBe (log2(array.size) + 1)
    }
    for (i <- array) {
      binarySearch(i, bst) shouldBe true
    }
  }

  "minBinaryTree" should "produce a minimal height BST" in {
    for (i <- (0 to 128)) {
      val numbers = for (j <- (0 until i)) yield {
        Random.nextInt()
      }
      testMinBinaryTree(numbers.sorted.toArray)
    }
  }

  def javaListsOfListsToScala[T](lists: java.util.List[java.util.List[T]]): List[List[T]] = {
    lists.map(_.toList).toList
  }

  def listsOfDepths[T](tree: Tree[T]): List[List[T]] = {
    javaListsOfListsToScala(Chapter4Solutions.listsOfDepths(tree))
  }

  "listsOfDepths" should "give lists of the elements at each depth" in {
    /*
     *            1
     *         2      4
     *       3   *  *    5
     *     *  *         *  *
     */
    val tree = new Tree(
        1,
        new Tree(2, new Tree(3), null), new Tree(4, null, new Tree(5))
    )
    listsOfDepths(tree) shouldBe List(
        List(1),
        List(2, 4),
        List(3, 5)
    )
    listsOfDepths(null) shouldBe List()
    listsOfDepths(new Tree(1)) shouldBe List(List(1))
  }

  "isBalanced" should "check if a tree is balanced" in {
    Chapter4Solutions.isBalanced(null) shouldBe true
    Chapter4Solutions.isBalanced(new Tree(1)) shouldBe true
    Chapter4Solutions.isBalanced(new Tree(1, new Tree(2), new Tree(3))) shouldBe true
    Chapter4Solutions.isBalanced(new Tree(1, new Tree(2, new Tree(4), null), null)) shouldBe true
    Chapter4Solutions.isBalanced(new Tree(1, new Tree(2, null, new Tree(4)), null)) shouldBe true
    Chapter4Solutions.isBalanced(new Tree(1, null, new Tree(2, null, new Tree(4)))) shouldBe true
    Chapter4Solutions.isBalanced(new Tree(1, null, new Tree(2, new Tree(4), null))) shouldBe true
  }
}