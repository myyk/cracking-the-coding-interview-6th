package com.github.myyk.cracking;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

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
   * Space complexity: O(log n) in a balanced tree due to recursive call stacks
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

  public static class BinarySearchTree<T extends Comparable<T>> extends Tree<T> {
    private final BinarySearchTree<T> parent;

    public BinarySearchTree(T data) {
      super(data);
      this.parent = null;
    }

    public BinarySearchTree(T data, BinarySearchTree<T> parent) {
      super(data);
      this.parent = parent;
    }

    public BinarySearchTree<T> getParent() {
      return parent;
    }

    public BinarySearchTree<T> setLeft(T value) {
      setLeft(new BinarySearchTree<T>(value, this));
      return this;
    }

    public BinarySearchTree<T> setRight(T value) {
      setRight(new BinarySearchTree<T>(value, this));
      return this;
    }

    @Override
    public BinarySearchTree<T> getLeft() {
      return (BinarySearchTree<T>) super.getLeft();
    }

    @Override
    public void setLeft(Tree<T> left) {
      if (!(left instanceof BinarySearchTree<?>)) {
        throw new IllegalArgumentException("left must also be a BinarySearchTree");
      }
      super.setLeft(left);
    }

    @Override
    public BinarySearchTree<T> getRight() {
      return (BinarySearchTree<T>) super.getRight();
    }

    @Override
    public void setRight(Tree<T> right) {
      if (!(right instanceof BinarySearchTree<?>)) {
        throw new IllegalArgumentException("right must also be a BinarySearchTree");
      }
      super.setRight(right);
    }
  }

  /**
   * Successor: Find the in-order successor to the given node in a BST. Nodes have pointers to parent nodes.
   *
   * Assumptions:
   *   duplicates are valid
   *   left <= current < right
   *
   * Time complexity: O(log n) in balanced tree
   * Space complexity: O(log n) in balanced tree
   */
  public static <T extends Comparable<T>> BinarySearchTree<T> findSuccessor(BinarySearchTree<T> bst) {
    if (bst == null) {
      return null;
    }

    if (bst.getRight() != null) {
      return findSuccessorDown(bst.getRight());
    } else {
      return findSuccessorUp(bst);
    }
  }

  private static <T extends Comparable<T>> BinarySearchTree<T> findSuccessorDown(BinarySearchTree<T> bst) {
    if (bst == null) {
      return null;
    }

    BinarySearchTree<T> successor = findSuccessorDown(bst.getLeft());
    if (successor != null) {
      return successor;
    }
    return bst;
  }

  private static <T extends Comparable<T>> BinarySearchTree<T> findSuccessorUp(BinarySearchTree<T> bst) {
    if (bst.getParent() == null) {
      return null;
    }
    if (bst.getParent().getLeft() == bst) {
      return bst.getParent();
    } else {
      return findSuccessorUp(bst.getParent());
    }
  }

  /**
   * Build Order: Projects build dependencies are on other projects. Given a list of project build dependencies
   * find a build order that can build all the projects. Throw an exception if it cannot be done.
   *
   * Assumptions:
   *
   * Time complexity: O(p + d) where p is number of projects and d is number of dependencies
   * Space complexity: O(n)
   *
   * Notes: This is basically toposort with cycle detection. This is a super common problem.
   */
  public static <T> List<Node<T>> findBuildOrder(Graph<T> projects) {
    final List<Node<T>> buildOrder = Lists.newLinkedList();
    final Set<Node<T>> rootProjects = findRoots(projects.getNodes());
    final LinkedList<Node<T>> queue = Lists.newLinkedList(rootProjects);
    final Set<Node<T>> marked = Sets.newHashSet();

    while (!queue.isEmpty()) {
      Node<T> next = queue.removeFirst();
      buildOrder.add(next);
      for (Node<T> adj : next.getAdjacent()) {
        if (marked.add(adj)) {
          queue.addLast(adj);
        }
      }
    }

    if (buildOrder.size() != projects.getNodes().size()) {
      throw new IllegalArgumentException("The projects cannot be built because there is a cycle.");
    }
    return buildOrder;
  }

  /**
   * Time complexity: O(p + d) where p is number of projects and d is number of dependencies
   * Space complexity: O(n)
   */
  private static <T> Set<Node<T>> findRoots(Set<Node<T>> nodes) {
    final Set<Node<T>> nonRoots = Sets.newHashSet();
    for (Node<T> next : nodes) {
      for (Node<T> adj : next.getAdjacent()) {
        nonRoots.add(adj);
      }
    }
    nodes.removeAll(nonRoots);
    return nodes;
  }

  /**
   * First Common Ancestor: Write an algorithm to find the first common ancestor between two nodes in
   *   a given tree. Avoid storing additional nodes in a data structure.
   *
   * Assumptions:
   *   there may be no ancestor
   *   if the two nodes are the same, it is the ancestor
   *   if one is the parent to the other, it is the ancestor
   *   no parent pointers
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   *
   * Notes: This is pretty complicated to get right during an interview.
   */
  public static <T> Tree<T> findFirstCommonAncestor(Tree<T> tree, Tree<T> p, Tree<T> q) {
    AncestorResult<T> result = findFirstCommonAncestorHelper(tree, p, q);
    return (result.isAnswer ? result.tree : null);
  }

  private static class AncestorResult<T> {
    private final boolean isAnswer;
    private final Tree<T> tree;
    public AncestorResult(boolean isAnswer, Tree<T> tree) {
      this.isAnswer = isAnswer;
      this.tree = tree;
    }    
  }

  /**
   * Return either the answer. Or if only p is found in tree, then p. Otherwise if only q is found in tree,
   * then q. Otherwise null.
   */
  public static <T> AncestorResult<T> findFirstCommonAncestorHelper(Tree<T> tree, Tree<T> p, Tree<T> q) {
    if (tree == null) {
      return new AncestorResult<T>(false, null);
    }
    if (tree == p && tree == q) {
      return new AncestorResult<T>(true, tree);
    }

    AncestorResult<T> resultLeft = findFirstCommonAncestorHelper(tree.getLeft(), p, q);
    if (resultLeft.isAnswer) {
      return resultLeft;
    }

    AncestorResult<T> resultRight = findFirstCommonAncestorHelper(tree.getRight(), p, q);
    if (resultRight.isAnswer) {
      return resultRight;
    }

    if (resultLeft.tree != null && resultRight.tree != null ) {
      return new AncestorResult<T>(true, tree);
    } else if (q == tree || p == tree) {
      boolean isAncestor = resultLeft.tree != null || resultRight.tree != null;
      return new AncestorResult<T>(isAncestor, tree);
    } else {
      return new AncestorResult<T>(false, resultLeft.tree != null ? resultLeft.tree : resultRight.tree);
    }
  }

  /**
   * BST Sequences: A BST was created from an array by traversing from right to left and inserting into
   *   the tree. Given a BST, return all the possible arrays that could have created it.
   *
   * Assumptions:
   *   no duplicates
   *
   * Time complexity: O() ?
   * Space complexity: O((log n)^2) ?
   *
   * Notes: Wow, this is hard in Java.
   */
  @SuppressWarnings("unchecked")
  public static <T> List<LinkedList<T>> bstSequences(Tree<T> tree) {
    if (tree == null) {
      return null;
    }
    final List<LinkedList<T>> results = Lists.newArrayList();
    final LinkedList<T> result = Lists.newLinkedList();
    if (tree.getLeft() == null && tree.getRight() == null) {
      result.add(tree.getData());
      results.add(result);
      return results;
    }

    List<LinkedList<T>> leftSequences = bstSequences(tree.getLeft());
    List<LinkedList<T>> rightSequences = bstSequences(tree.getRight());

    if (leftSequences == null || rightSequences == null) {
      return leftSequences == null ? rightSequences : leftSequences;
    }
    LinkedList<T> prefix = Lists.newLinkedList();
    prefix.add(tree.getData());
    for (LinkedList<T> leftSequence : leftSequences) {
      for (LinkedList<T> rightSequence : rightSequences) {
        List<LinkedList<T>> weaved = weaveSequences(leftSequence, rightSequence, Lists.<LinkedList<T>>newArrayList(), prefix);
        results.addAll(weaved);
      }
    }
    return results;
  }

  protected static <T> List<LinkedList<T>> weaveSequences(LinkedList<T> first, LinkedList<T> second, List<LinkedList<T>> results, LinkedList<T> prefix) {
    if (first.isEmpty() || second.isEmpty()) {
      @SuppressWarnings("unchecked")
      LinkedList<T> result = (LinkedList<T>) prefix.clone();
      result.addAll(first);
      result.addAll(second);
      results.add(result);
      return results;
    }

    T firstHead = first.removeFirst();
    prefix.addLast(firstHead);
    weaveSequences(first, second, results, prefix);
    //restore for other calls
    prefix.removeLast();
    first.addFirst(firstHead);

    T secondHead = second.removeFirst();
    prefix.addLast(secondHead);
    weaveSequences(first, second, results, prefix);
    //restore for other calls
    prefix.removeLast();
    second.addFirst(secondHead);

    return results;
  }

  /**
   * Check Subtree: Given two trees, T1 and T2, where T1 is very large and much larger than T2.
   *   Find out if T2 is a subtree of T1.
   *
   * Assumptions:
   *
   * Time complexity: O()
   * Space complexity: O()
   */
  public static <T> boolean isSubtree(Tree<T> t1, Tree<T> t2) {
    return false;
  }
}
