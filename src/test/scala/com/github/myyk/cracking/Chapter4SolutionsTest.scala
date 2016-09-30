package com.github.myyk.cracking

import org.scalatest.FlatSpec
import org.scalatest.Matchers
import com.github.myyk.cracking.Chapter4Solutions.IntGraph
import com.github.myyk.cracking.Chapter4Solutions.IntNode
import com.github.myyk.cracking.Chapter4Solutions.Node

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
}