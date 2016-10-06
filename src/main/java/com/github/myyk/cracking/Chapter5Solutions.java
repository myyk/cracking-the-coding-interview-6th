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

  /**
   * Flip Bit to Win: Given a 32-bit integer and the ability to flip one bit from a 0 to a 1, find the
   *   longest length of sequences of 1s.
   *
   * Assumptions:
   *
   * Time complexity: O(n) where n is the number of bits, which means O(1) for 32-bit, but this could be
   *   general enough to use for longer inputs
   * Space complexity: O(1)
   *
   * Note: Unless I've done something wrong that I don't understand at all. I think my algorithm is much
   *   better/simpler than the book's optimal answer.
   */
  public static int flipBitToWin(int num) {
    if (num == -1) {
      return 32;
    }

    int longest = 1;
    int last = 0;
    int current = 0;

    for (int i = 0; i < 32; i++) {
      int mask = 1 << i;
      int bitSet = num & mask;
      if (bitSet != 0) {
        current++;
        longest = Math.max(longest, last + current + 1);
      } else {
        last = current;
        current = 0;
      }
    }

    return longest;
  }

  public static class NextNumberResult {
    int smaller;
    int larger;
  }

  /**
   * Next Number: Given a positive integer, return the next smaller and next larger integer that have the
   *   same number of 1s as the given integer.
   *
   * Assumptions:
   *   0 should return null
   *   positive number
   *
   * Time complexity: O()
   * Space complexity: O()
   *
   */
  public static NextNumberResult nextNumber(int num) {
    return null;
  }
}
