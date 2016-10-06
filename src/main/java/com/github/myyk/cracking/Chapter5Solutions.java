package com.github.myyk.cracking;

/**
 * Bit Manipulation
 */
public class Chapter5Solutions {

  /**
   * Insertion: Given 2 32-bit numbers N and M and two indexes i and j. Insert M into M such that it starts
   * at bit j and ends at bit i.
   *
   * Assumptions:
   *   i and j have enough bits between them to fit M.
   *   if there are already bits set in N between i and j, they will be overwritten
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   */
  public static int insert(int n, int m, int j, int i) {
    n &= (-1 << (j + 1)) | ((1 << i) - 1);
    n |= (m << i);
    return n;
  }
}
