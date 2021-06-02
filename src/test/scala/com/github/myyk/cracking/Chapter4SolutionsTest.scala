package com.github.myyk.cracking

import com.github.myyk.cracking.Chapter4Solutions._
import com.google.common.collect.Lists

import java.util
import scala.annotation.tailrec
import scala.jdk.CollectionConverters._
import scala.util.Random

import org.scalatest._
import flatspec._
import matchers._

class Chapter4SolutionsTest extends AnyFlatSpec with should.Matchers {
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
    val (_, nodes) = createMediumDirectionalTestGraph
    Chapter4Solutions.pathExistsDirectional(nodes.head, nodes.head) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes.head, nodes(1)) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes(1), nodes.head) shouldBe false
    Chapter4Solutions.pathExistsDirectional(nodes.head, nodes(6)) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes(6), nodes.head) shouldBe false
    Chapter4Solutions.pathExistsDirectional(nodes.head, nodes(7)) shouldBe false
    Chapter4Solutions.pathExistsDirectional(nodes(6), nodes(1)) shouldBe true
    Chapter4Solutions.pathExistsDirectional(nodes(1), nodes(6)) shouldBe true
  }

  "pathExistsBidirectional" should "find if there is a path between a and b" in {
    val (_, nodes) = createMediumBidirectionalTestGraph
    Chapter4Solutions.pathExistsBidirectional(nodes.head, nodes.head) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes.head, nodes(1)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(1), nodes.head) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes.head, nodes(6)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(6), nodes.head) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(6), nodes(1)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes(1), nodes(6)) shouldBe true
    Chapter4Solutions.pathExistsBidirectional(nodes.head, nodes(7)) shouldBe false
    Chapter4Solutions.pathExistsBidirectional(nodes(7), nodes.head) shouldBe false
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
      getHeight(bst) shouldBe (log2(array.length) + 1)
    }
    for (i <- array) {
      binarySearch(i, bst) shouldBe true
    }
  }

  "minBinaryTree" should "produce a minimal height BST" in {
    for (i <- 0 to 128) {
      val numbers = for (_ <- 0 until i) yield {
        Random.nextInt()
      }
      testMinBinaryTree(numbers.sorted.toArray)
    }
  }

  def javaListsOfListsToScala[T](lists: java.util.List[java.util.List[T]]): List[List[T]] = {
    lists.asScala.map(_.asScala.toList).toList
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

  "isValidBST" should "check if a tree is balanced" in {
    // null, single, and simple
    Chapter4Solutions.isValidBST(null) shouldBe true
    Chapter4Solutions.isValidBST(new Tree[Integer](1)) shouldBe true
    Chapter4Solutions.isValidBST(new Tree[Integer](1, new Tree(2), null)) shouldBe false
    Chapter4Solutions.isValidBST(new Tree[Integer](1, new Tree(0), null)) shouldBe true
    Chapter4Solutions.isValidBST(new Tree[Integer](1, null, new Tree(2))) shouldBe true
    Chapter4Solutions.isValidBST(new Tree[Integer](1, null, new Tree(0))) shouldBe false
    Chapter4Solutions.isValidBST(new Tree[Integer](1, new Tree(0), new Tree(2))) shouldBe true

    // duplicates
    Chapter4Solutions.isValidBST(new Tree[Integer](1, new Tree(1), null)) shouldBe true
    Chapter4Solutions.isValidBST(new Tree[Integer](1, null, new Tree(1))) shouldBe false
    Chapter4Solutions.isValidBST(new Tree[Integer](1, new Tree(1), new Tree(1))) shouldBe false

    Chapter4Solutions.isValidBST(Chapter4Solutions.minBinaryTree((0 until 1000).map(_ => Random.nextInt()).sorted.toArray)) shouldBe true
    Chapter4Solutions.isValidBST(Chapter4Solutions.minBinaryTree((0 until 1000).map(_ => Random.nextInt()).toArray)) shouldBe false

    // right grandchild greater than node
    Chapter4Solutions.isValidBST(new Tree[Integer](20, new Tree[Integer](10, null, new Tree(25)), new Tree(30))) shouldBe false
  }

  "findSuccessor" should "find the in-order successor to the node" in {
    Chapter4Solutions.findSuccessor(null: BinarySearchTree[Integer]) shouldBe null
    Chapter4Solutions.findSuccessor(new BinarySearchTree[Integer](1)) shouldBe null
    Chapter4Solutions.findSuccessor(new BinarySearchTree[Integer](2).setLeft(1)) shouldBe null
    val bst1 = new BinarySearchTree[Integer](2).setRight(3)
    bst1.getRight.setRight(4)
    Chapter4Solutions.findSuccessor(bst1) shouldBe bst1.getRight
    Chapter4Solutions.findSuccessor(bst1.getRight) shouldBe bst1.getRight.getRight
    Chapter4Solutions.findSuccessor(bst1.getRight.getRight) shouldBe null
    val bst2 = new BinarySearchTree[Integer](20).setLeft(10).setRight(30)
    bst2.getLeft.setRight(15)
    Chapter4Solutions.findSuccessor(bst2.getLeft) shouldBe bst2.getLeft.getRight
    Chapter4Solutions.findSuccessor(bst2.getLeft.getRight) shouldBe bst2
  }

  "findBuildOrder" should "find a build order for the provided projects" in {
    val projects = new Graph[Char]()
    val a = new Node('a')
    val b = new Node('b')
    val c = new Node('c')
    val d = new Node('d')
    val e = new Node('e')
    val f = new Node('f')
    a addAdjacent d
    f addAdjacent b
    b addAdjacent d
    f addAdjacent a
    d addAdjacent c
    projects.addNode(a)
    projects.addNode(b)
    projects.addNode(c)
    projects.addNode(d)
    projects.addNode(e)
    projects.addNode(f)

    Chapter4Solutions.findBuildOrder(projects).size shouldBe 6
    // This can be multiple different correct answers. Would need a checkBuildOrder function to test this.
//    Chapter4Solutions.findBuildOrder(projects).map(_.getData) shouldBe List(e, f, b, a, d, c).map(_.getData)
  }

  "findBuildOrder" should "not find a build order for the provided projects" in {
    val projects = new Graph[Char]()
    val a = new Node('a')
    a addAdjacent a
    projects.addNode(a)

    intercept[IllegalArgumentException] {
      Chapter4Solutions.findBuildOrder(projects)
    }

    val b = new Node('b')
    projects.addNode(b)

    intercept[IllegalArgumentException] {
      Chapter4Solutions.findBuildOrder(projects)
    }
  }

  "findFirstCommonAncestor" should "find the first common ancestor" in {
    /*
     *      1
     *   2     3
     * 4     6   7
     */
    val tree = new Tree(1, new Tree(2, new Tree(4), null), new Tree(3, new Tree(6), new Tree(7)))
    Chapter4Solutions.findFirstCommonAncestor(tree, tree, tree) shouldBe tree
    Chapter4Solutions.findFirstCommonAncestor(tree, tree, new Tree(-999)) shouldBe null
    Chapter4Solutions.findFirstCommonAncestor(tree, new Tree(-999), tree) shouldBe null
    Chapter4Solutions.findFirstCommonAncestor(tree, tree.getRight.getRight, tree) shouldBe tree
    Chapter4Solutions.findFirstCommonAncestor(tree, tree, tree.getRight.getRight) shouldBe tree
    Chapter4Solutions.findFirstCommonAncestor(tree, tree.getLeft, tree) shouldBe tree
    Chapter4Solutions.findFirstCommonAncestor(tree, tree, tree.getLeft) shouldBe tree
    Chapter4Solutions.findFirstCommonAncestor(tree, tree.getRight.getRight, tree.getRight.getLeft) shouldBe tree.getRight
    Chapter4Solutions.findFirstCommonAncestor(tree, tree.getLeft.getLeft, tree.getRight.getRight) shouldBe tree
  }

  "bstSequences" should "give all the sequences that could have created it" in {
    Chapter4Solutions.bstSequences[Int](null) shouldBe null
    Chapter4Solutions.bstSequences(new Tree(1, new Tree(2), new Tree(3))).asScala.map(_.asScala.toList).toSet shouldBe Set(List(1,2,3), List(1,3,2))
  }

  "weaveSequences" should "weave the two sequences together" in {
    val list1 = Lists.newLinkedList[Int]
    list1.add(1)
    val list2 = Lists.newLinkedList[Int]
    list2.add(2)
    val prefix = Lists.newLinkedList[Int]
    prefix.add(3)
    Chapter4Solutions.weaveSequences(list1, list2, Lists.newLinkedList[util.LinkedList[Int]](), prefix).asScala.map(_.asScala.toList).toSet shouldBe Set(List(3,1,2), List(3,2,1))
  }

  def testIsSubtree(isSubtreeOp: (Tree[Int], Tree[Int]) => Boolean): Unit = {
    val t1 = new Tree(1, new Tree(2, new Tree(4), null), new Tree(3, new Tree(6), new Tree(7)))
    isSubtreeOp(null, t1) shouldBe false
    isSubtreeOp(t1, null) shouldBe true
    isSubtreeOp(t1, t1.getRight) shouldBe true
    isSubtreeOp(t1, t1.getLeft) shouldBe true
    isSubtreeOp(t1, t1) shouldBe true
    isSubtreeOp(t1, t1.getLeft.getLeft) shouldBe true
    isSubtreeOp(t1, t1.getRight.getLeft) shouldBe true
    isSubtreeOp(t1, t1.getRight.getRight) shouldBe true
  }

  "isSubtree" should "check if t2 is a subtree of t1" in {
    testIsSubtree(Chapter4Solutions.isSubtree)
  }

  "isSubtree2" should "check if t2 is a subtree of t1" in {
    testIsSubtree(Chapter4Solutions.isSubtree2)
  }

  "RandomTree" should "be able to return a random number" in {
    val tree = new RandomTree(1, new RandomTree(2), new RandomTree(3, new RandomTree(4), new RandomTree(5)))
    val results = for (_ <- 0 until 10000) yield {
      tree.getRandomNode.getData
    }
    val occurrences = results.groupBy(a => a).map{case (i, occurrences) => (i, occurrences.size)}.toList.sortBy{case (a, _) => a}.map{case (_, a) => a}
    // this should pass most of the time
    // max freq shouldn't be more than 20% of the samples more than the min
    (occurrences.max - occurrences.min) should be < occurrences.sum / occurrences.size / 5
  }

  "countPathsWithSum" should "count the number of paths with the provided sum going downwards in the tree" in {
    /*
     *            10
     *         /       \
     *       5          -3
     *     /  \            \
     *   3     2            11
     *  / \     \
     * 3  -2      1
     */
    val left = new Tree[Integer](5, new Tree[Integer](3, new Tree(3), new Tree(-2)), new Tree[Integer](2, null, new Tree(1)))
    val right = new Tree[Integer](-3, null, new Tree(11))
    val tree = new Tree[Integer](10, left, right)
    Chapter4Solutions.countPathsWithSum(tree, 10000) shouldBe 0
    Chapter4Solutions.countPathsWithSum(tree, 10) shouldBe 1
    Chapter4Solutions.countPathsWithSum(tree, 1) shouldBe 2
    Chapter4Solutions.countPathsWithSum(tree, 18) shouldBe 3
    Chapter4Solutions.countPathsWithSum(tree, 6) shouldBe 2
  }
}
