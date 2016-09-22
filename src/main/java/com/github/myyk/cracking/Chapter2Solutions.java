package com.github.myyk.cracking;

/**
 * Linked Lists
 */
public class Chapter2Solutions {
  public static class Node {
    int value;
    Node next;

    public Node(int value, Node next) {
      super();
      this.value = value;
      this.next = next;
    }
  }
  
  /**
   * Remove Dups: Remove duplicates from the unsorted linked list.
   *
   * Assumptions:
   *   singly linked list
   *   temporary buffer disallowed
   *
   * Time complexity: O(n^2)
   * Space complexity: O(1)
   */
  public static Node removeDups(Node n) {
    Node current = n;
    while (current != null) {
      Node runner = current;
      while (runner.next != null) {
        if (current.value == runner.next.value) {
          runner.next = runner.next.next;
        } else {
          runner = runner.next;
        }
      }
      current = current.next;
    }
    return n;
  }

  /**
   * Return Kth to Last: Return k-th to last element in singly linked list.
   *
   * Assumptions:
   *   return null if there aren't k+1 elements
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static Node kToLast(int k, Node n) {
    if (k<0 || n==null) {
      return null;
    }

    Node current = n;
    Node runner = n.next;

    for (int i = 0; i<k; i++) {
      if (runner == null) {
        return null;
      }
      runner = runner.next;
    }

    while (runner != null) {
      current = current.next;
      runner = runner.next;
    }

    return current;
  }

  /**
   * Delete Middle Node: Given node in a singly linked list, delete that node.
   *
   * Assumptions:
   *   n is not the last node
   *
   * Time complexity: O(1)
   * Space complexity: O(1)
   */
  public static void deleteMiddleNode(Node n) {
    n.value = n.next.value;
    n.next = n.next.next;
  }
}
