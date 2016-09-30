package com.github.myyk.cracking;

import java.util.List;
import java.util.Queue;
import java.util.Set;

import javafx.util.Pair;

import com.google.common.collect.Lists;
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

  public static class Tree<T> {
    private Tree<T> left, right;
    private T data;

    public Tree(T data) {
      super();
      this.data = data;
    }

    public Tree(T data, Tree<T> left, Tree<T> right) {
      super();
      this.left = left;
      this.right = right;
      this.data = data;
    }

    public Tree<T> getLeft() {
      return left;
    }

    public void setLeft(Tree<T> left) {
      this.left = left;
    }

    public Tree<T> getRight() {
      return right;
    }

    public void setRight(Tree<T> right) {
      this.right = right;
    }

    public T getData() {
      return data;
    }

    public void setData(T data) {
      this.data = data;
    }
  }

  /**
   * Minimal Tree: Given a sorted array of unique integers, provide a minimal height BST.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   */
  public static Tree<Integer> minBinaryTree(int[] array) {
    return minBinaryTree(array, 0, array.length);
  }

  private static Tree<Integer> minBinaryTree(int[] array, int start, int end) {
    if (start == end) {
      return null;
    } else {
      int mid = (start + end) / 2;
      return new Tree<Integer>(
          new Integer(array[mid]),
          minBinaryTree(array, start, mid),
          minBinaryTree(array, mid + 1, end)
      );
    }
  }

  /**
   * Lists of Depths: Given a binary, return lists containing the elements at each depth.
   *
   * Assumptions:
   *   order matters, left to right
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   */
  public static <T> List<List<T>> listsOfDepths(Tree<T> tree) {
    Queue<Tree<T>> queue = Queues.newArrayDeque();
    if (tree != null) {
      queue.add(tree);
    }
    Queue<Tree<T>> temp;

    List<List<T>> lists = Lists.newLinkedList();
    List<T> nextList;
    while (!queue.isEmpty()) {
      nextList = Lists.newLinkedList(); // could use arraylist of exact size if we wanted to calculate
      temp = Queues.newArrayDeque();
      while (!queue.isEmpty()) {
        Tree<T> next = queue.remove();
        nextList.add(next.getData());
        if (next.left != null) {
          temp.add(next.left);
        }
        if (next.right != null) {
          temp.add(next.right);
        }
      }
      queue = temp;
      lists.add(nextList);
    }

    return lists;
  }

  /**
   * Check Balanced: Checks if a given binary tree is balanced.
   *
   * Assumptions:
   *   balanced means any two branches from a node have at most a height difference of 1
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   * 
   * Timed coding portion: 3m33s
   */
  public static <T> boolean isBalanced(Tree<T> tree) {
    return isBalancedHelper(tree) != -1;
  }

  private static <T> int isBalancedHelper(Tree<T> tree) {
    if (tree == null) {
      return 0;
    } else {
      int heightLeft = isBalancedHelper(tree.left);
      if (heightLeft == -1) {
        return -1;
      }
      int heightRight = isBalancedHelper(tree.right);
      if (heightRight == -1) {
        return -1;
      }
      if (Math.abs(heightLeft - heightRight) > 1) {
        return -1;
      } else {
        return Math.max(heightLeft, heightRight);
      }
    }
  }

  /**
   * Validate BST: Checks if a given binary tree is a binary search tree.
   *
   * Assumptions:
   *   duplicates are valid
   *   left <= current < right
   *
   * Time complexity: O(n)
   * Space complexity: O(log n)
   *
   * Notes: Be careful about duplicates, they are okay on the left, not the right.
   */
  public static <T extends Comparable<T>> boolean isValidBST(Tree<T> tree) {
    if (tree == null) {
      return true;
    }

    return isValidBST(tree, null, null);
  }

  public static <T extends Comparable<T>> boolean isValidBST(Tree<T> tree, T min, T max) {
    if (tree == null) {
      return true;
    }

    if (max != null && tree.getData().compareTo(max) > 0) {
      return false;
    }
    if (min != null && tree.getData().compareTo(min) <= 0) {
      return false;
    }

    return isValidBST(tree.getLeft(), min, tree.getData()) && isValidBST(tree.getRight(), tree.getData(), max);
  }
}
