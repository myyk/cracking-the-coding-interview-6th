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
    Node prev = n;
    while (prev != null) {
      Node runnerPrev = n;
      Node runnerNext = prev.next;
      while (runnerNext != null) {
        if (prev.value == runnerNext.value) {
          runnerPrev.next = runnerNext.next;
        } else {
          runnerPrev = runnerPrev.next;
        }
        runnerNext = runnerNext.next;
      }
      prev = prev.next;
    }
    return n;
  }
}
