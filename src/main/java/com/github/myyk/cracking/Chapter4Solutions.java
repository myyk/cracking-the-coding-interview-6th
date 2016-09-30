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
   * Time complexity: O()
   * Space complexity: O()
   */
  public static boolean pathExistsDirectional(IntNode a, IntNode b, IntGraph graph) {
    return false;
  }
  
  /**
   * Route Between Nodes: Modified - Find whether there is a path between two nodes (A->B) in a bidirectional graph.
   *
   * Assumptions:
   *
   * Time complexity: O()
   * Space complexity: O()
   */
  public static boolean pathExistsBidirectional(IntNode a, IntNode b, IntGraph graph) {
    System.out.println("==================");
    if (a == b) {
      return true;
    }

    // BFS on both nodes at the same time
    Queue<ReachableNode<Integer>> queue = Queues.newArrayDeque();
    Set<ReachableNode<Integer>> visited = Sets.newHashSet();

    visited.add(new ReachableNode<Integer>(a, a));
    visited.add(new ReachableNode<Integer>(b, b));
    queue.add(new ReachableNode<Integer>(a, a));
    queue.add(new ReachableNode<Integer>(b, b));

    while (!queue.isEmpty()) {
      ReachableNode<Integer> next = queue.remove();
      IntNode destination = (IntNode) getOppositeNode((Node<Integer>) a, (Node<Integer>) b, next.getFrom());
      System.out.println("visiting " + next.getNode().getData());
      for (Node<Integer> adjacent : next.getNode().getAdjacent()) {
        if (visited.contains(new ReachableNode<Integer>(destination, next.getNode()))) {
          System.out.println("FOUND dest = " + destination.getData() + "  from = " + next.getFrom().getData() + "  next = "+ next.getNode().getData());
          return true;
        } else if (visited.add(new ReachableNode<Integer>(next.getFrom(), adjacent))) {
          System.out.println("queuing " + adjacent.getData());
          queue.add(new ReachableNode<Integer>(next.getFrom(), adjacent));
        }
      }
    }

    return false;
  }

  // give opposite node
  private static <T> Node<T> getOppositeNode(Node<T> a, Node<T> b, Node<T> node) {
    if (a == node) {
      return b;
    } else {
      return a;
    }
  }

  private static class ReachableNode<T> {
    private final Node<T> from;
    private final Node<T> node;
    public ReachableNode(Node<T> from, Node<T> node) {
      super();
      this.from = from;
      this.node = node;
    }
    public Node<T> getFrom() {
      return from;
    }
    public Node<T> getNode() {
      return node;
    }
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((from == null) ? 0 : from.hashCode());
      result = prime * result + ((node == null) ? 0 : node.hashCode());
      return result;
    }
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ReachableNode other = (ReachableNode) obj;
      if (from == null) {
        if (other.from != null)
          return false;
      } else if (!from.equals(other.from))
        return false;
      if (node == null) {
        if (other.node != null)
          return false;
      } else if (!node.equals(other.node))
        return false;
      return true;
    }
  }
}
