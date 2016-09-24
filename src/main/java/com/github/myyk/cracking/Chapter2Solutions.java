package com.github.myyk.cracking;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

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

  /**
   * Partition: Partition list around x, if x is also contained it just has to be after values
   *   less than it.
   *
   * Assumptions:
   *  not stable
   *  not in place
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static Node partition(int x, Node n) {
    if (n==null) {
      return null;
    }

    Node under = n;
    Node over = n;
    Node current = n;
    while (current != null) {
      Node next = current.next;
      if (current.value < x) {
        current.next = under;
        under = current;
      } else {
        over.next = current;
        over = current;
      }
      current = next;
    }
    over.next = null;

    return under;
  }

  /**
   * Sum Lists (1s first): single digit stored in each element, 1s first. Add the numbers.
   *
   * Assumptions:
   *   it is better to do a few extra operations on each pair of nodes to not do any operations when there isn't a pair or carry.
   *
   * Improved on the books' answer by optimizing when one list is much longer than another list to not traverse.
   *
   * Time complexity: O(max(n,m))
   * Space complexity: O(max(n,m))
   */
  public static Node sumLists1(Node n1, Node n2) {
    return sumLists1Helper(n1, n2, false);
  }

  private static Node sumLists1Helper(Node a, Node b, Boolean carry) {
    int sum = 0;
    Node nextA = null;
    Node nextB = null;
    if (a != null) {
      sum += a.value;
      nextA = a.next;
    }
    if (b != null) {
      sum += b.value;
      nextB = b.next;
    }
    sum = carry ? (sum + 1) : sum;
    boolean nextCarry = (sum / 10 > 0);

    if (sum == 0 && a == null && b == null) {
      return null;
    } else if (a==null && !nextCarry) {
      return new Node(sum, nextB);
    } else if (b==null && !nextCarry) {
      return new Node(sum, nextA);
    } else {
      return new Node(sum%10, sumLists1Helper(nextA, nextB, nextCarry));
    }
  }

  /**
   * Sum Lists (1s first): single digit stored in each element, 1s last. Add the numbers.
   *
   * Assumptions:
   *   the interviewer wants me to not just reverse the things and write something convoluted instead
   *
   * Time complexity: O(max(n,m))
   * Space complexity: O(max(n,m))
   */
  public static Node sumLists2(Node n1, Node n2) {
    return sumLists2Helper(n1, n2);
  }
  
  private static class SumResult {
    Node sum;
    boolean carry;

    public SumResult(Node sum, boolean carry) {
      this.sum = sum;
      this.carry = carry;
    }
  }

  private static Node sumLists2Helper(Node a, Node b) {
    int lenA = findLength(a);
    int lenB = findLength(b);

    //pad shorter
    if (lenA < lenB) {
      a = padZeros(a, lenB - lenA);
    } else if (lenA > lenB) {
      b = padZeros(b, lenA - lenB);
    }

    SumResult result = sumHelperHelper(a, b);
    if (result.carry) {
      return new Node(1, result.sum);
    } else {
      return result.sum;
    }
  }

  private static SumResult sumHelperHelper(Node a, Node b) {
    if (a == null) {
      return new SumResult(null, false);
    } else {
      SumResult sumRight = sumHelperHelper(a.next, b.next);
      int sum = a.value + b.value + (sumRight.carry ? 1 : 0);
      return new SumResult(new Node(sum%10, sumRight.sum), (sum/10 == 1));
    }
  }

  private static int findLength(Node n) {
    int length = 0;
    while (n != null) {
      length++;
      n = n.next;
    }
    return length;
  }

  private static Node padZeros(Node n, int zeros) {
    if (zeros <= 0) {
      return n;
    } else {
      return new Node(0, padZeros(n, zeros-1));
    }
  }

  /**
   * Palindrome: Are the contents of a singly linked list palindomic.
   *
   * Assumptions:
   *   length is not given
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   */
  public static boolean isPalindrome(Node n) {
    if (n == null || n.next == null) {
      return true;
    }

    Node current = n;
    Node runner = n;
    Stack<Integer> stack = new Stack<Integer>();

    while (runner != null && runner.next != null) {
      stack.push(current.value);
      current = current.next;
      runner = runner.next.next;
    }

    // odd number of elements, skip the middle element
    if (runner != null) {
      current = current.next;
    }

    while (current != null) {
      if (current.value != stack.pop()) {
        return false;
      }
      current = current.next;
    }

    return true;
  }

  /**
   * Intersection: Find where two singly linked lists intersect if they do.
   *
   * Assumptions:
   *   intersection is done by reference equality
   *   the lists aren't too different in length
   *
   * Time complexity: O(n+m)
   * Space complexity: O(n) when n is the length of the first list
   */
  public static Node findInteresection(Node a, Node b) {
    Node current = a;
    Set<Node> nodes = new HashSet<Node>();
    while (current != null) {
      nodes.add(current);
      current = current.next;
    }

    current = b;
    while (current != null) {
      if (nodes.contains(current)) {
        return current;
      }
      current = current.next;
    }

    return null;
  }

  /**
   * Loop Detection: Find where a loop started if there is one in the singly linked list.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static Node findLoopStart(Node n) {
    Node slow = n;
    Node fast = n;
    while (fast != null && fast.next != null) {
      slow = slow.next;
      fast = fast.next.next;
      if (slow == fast) // tricky, do this here instead of in while or else breaks on start
        break;
    }

    if (fast == null || fast.next == null) {
      return null;
    }

    slow = n;
    while (slow != fast) {
      slow = slow.next;
      fast = fast.next;
    }

    return slow;
  }
}
