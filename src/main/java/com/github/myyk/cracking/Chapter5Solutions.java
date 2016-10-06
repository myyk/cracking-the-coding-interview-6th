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
   * Time complexity: O(1)
   * Space complexity: O(1)
   */
  public static int insert(int n, int m, int j, int i) {
    n &= (-1 << (j + 1)) | ((1 << i) - 1);
    n |= (m << i);
    return n;
  }

  /**
   * Binary To String: Given a real number between 0 and 1. Return the binary representation as a string.
   *
   * Assumptions:
   *   If it doesn't fit in 32 bits, return "ERROR"
   *
   * Time complexity: O(1)
   * Space complexity: O(1)
   */
  public static String floatToBinaryString(double num) {
    if (num >= 1 || num <= 0) {
      return "ERROR";
    }

    StringBuffer buf = new StringBuffer(32);
    buf.append('.');

    while (num > 0) {
      if (buf.length() >= 32) {
        return "ERROR";
      }

      num = num * 2;
      if (num >= 1) {
        buf.append("1");
        num -= 1;
      } else {
        buf.append("0");
      }
    }
    return buf.toString();
  }
}
