package com.github.myyk.cracking;

import java.util.Queue;
import java.util.Set;

import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

/**
 * Trees and Graphs
 */
public class Chapter4Solutions {

  public static class Graph<T> {
    private final Set<Node<T>> nodes = Sets.newHashSet();

    public Set<Node<T>> getNodes() {
      return Sets.newHashSet(nodes);
    }

    public void addNode(Node<T> node) {
      nodes.add(node);
    }

    // returns whether or not a node was removed
    public boolean removeNode(Node<T> node) {
      return nodes.remove(node);
    }
  }

  public static class Node<T> {
    private final T data;
    private final Set<Node<T>> adjacent = Sets.newHashSet();

    public Set<Node<T>> getAdjacent() {
      return adjacent;
    }

    public Node(T data) {
      this.data = data;
    }

    public T getData() {
      return data;
    }

    // returns if the node was added, false if already there
    public boolean addAdjacent(Node<T> node) {
      return adjacent.add(node);
    }

    // returns true if any were added
    public boolean addAdjacents(Set<Node<T>> nodes) {
      return adjacent.addAll(nodes);
    }
  }

  // to make faster to write code these classes might help
  public static class IntGraph extends Graph<Integer> {}
  public static class IntNode extends Node<Integer> {
    public IntNode(Integer data) {
      super(data);
    }
  }

  /**
   * Route Between Nodes: Find whether there is a path between two nodes (A->B) in a directed graph.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   *
   * Notes: Simple breadth first search.
   */
  public static boolean pathExistsDirectional(IntNode a, IntNode b, IntGraph graph) {
    if (a == b) {
      return true;
    }

    Queue<IntNode> queue = Queues.newArrayDeque();
    Set<IntNode> visited = Sets.newHashSet();
    queue.add(a);
    visited.add(a);

    while (!queue.isEmpty()) {
      IntNode next = queue.remove();
      for (Node<Integer> adjacent : next.getAdjacent()) {
        if (adjacent == b) {
          return true;
        } else if (visited.add((IntNode) adjacent)) {
          queue.add((IntNode) adjacent);
        }
      }  
    }

    return false;
  }
  
  /**
   * Route Between Nodes: Modified - Find whether there is a path between two nodes (A->B) in a bidirectional graph.
   *
   * Assumptions:
   *
   * Time complexity: O(n) where n is numer of nodes
   * Space complexity: O(n)
   */
  public static boolean pathExistsBidirectional(IntNode a, IntNode b) {
    // BFS on both nodes at the same time
    Queue<IntNode> queueA = Queues.newArrayDeque();
    Queue<IntNode> queueB = Queues.newArrayDeque();
    Set<IntNode> visitedA = Sets.newHashSet();
    Set<IntNode> visitedB = Sets.newHashSet();

    visitedA.add(a);
    visitedB.add(b);
    queueA.add(a);
    queueB.add(b);

    while (!queueA.isEmpty() && !queueB.isEmpty()) {
      if (pathExistsBidirectionalHelper(queueA, visitedA, visitedB)) {
        return true;
      }
      if (pathExistsBidirectionalHelper(queueB, visitedB, visitedA)) {
        return true;
      }
    }

    return false;
  }

  private static boolean pathExistsBidirectionalHelper(Queue<IntNode> queue, Set<IntNode> visitedFromThisSide, Set<IntNode> visitedFromThatSide) {
    if (!queue.isEmpty()) {
      IntNode next = queue.remove();
      for (Node<Integer> adjacent : next.getAdjacent()) {
        if (visitedFromThatSide.contains(adjacent)) {
          return true;
        } else if (visitedFromThisSide.add((IntNode) adjacent)) {
          queue.add((IntNode) adjacent);
        }
      }
    }
    return false;
  }
}
