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

  
}
