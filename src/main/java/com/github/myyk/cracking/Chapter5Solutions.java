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
    final int smaller;
    final int larger;

    @Override
    public String toString() {
      return "NextNumberResult [smaller=" + smaller + ", larger=" + larger + "]";
    }
    public NextNumberResult(int smaller, int larger) {
      super();
      this.smaller = smaller;
      this.larger = larger;
    }
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + larger;
      result = prime * result + smaller;
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
      NextNumberResult other = (NextNumberResult) obj;
      if (larger != other.larger)
        return false;
      if (smaller != other.smaller)
        return false;
      return true;
    }
  }

  /**
   * Next Number: Given a positive integer, return the next smaller and next larger integer that have the
   *   same number of 1s as the given integer.
   *
   * Assumptions:
   *   if there are no numbers smaller or larger with the same number of 1s, use -1 in that spot
   *   positive number
   *
   * Time complexity: O(n) where n is the number of bits in the integer, so O(1) for fixed size
   * Space complexity: O(1)
   */
  public static NextNumberResult nextNumber(int num) {
    if (num <= 0) {
      return new NextNumberResult(-1, -1);
    }

    int smaller = nextSmallerNumber(num);
    int larger = nextLargerNumber(num);

    // handle edge cases
    if (larger <= num) {
      larger = -1;
    }
    if (smaller >= num || smaller <= 0) {
      smaller = -1;
    }

    return new NextNumberResult(smaller, larger);
  }

  // counts first two blocks of 0s and 1s from right to left where it starts with startsWith
  // array[0] is the first block
  private static int[] countBlocks(int num, int startsWith) {
    if (startsWith != 0 && startsWith != 1) { throw new IllegalArgumentException("startsWith = [" + startsWith + "] but should be 0 or 1."); }

    int c0 = 0;
    int c1 = 0;
    for (int i = 0; i < 32; i++) {
      int maskedBit = num>>i & 1;
      boolean isFirstType = maskedBit == startsWith;
      if (isFirstType) {
        if (c1 == 0) {
          c0++;
        } else {
          break;
        }
      } else {
        c1++;
      }
    }
    return new int[] { c0, c1 };
  }

  private static int nextLargerNumber(int num) {
    int[] c = countBlocks(num, 0);
    return num + (1 << c[0]) + ((1 << (c[1] - 1)) - 1);
  }

  private static int nextSmallerNumber(int num) {
    int[] c = countBlocks(num, 1);
    return num - (1 << c[0]) - ((1 << (c[1] - 1)) - 1);
  }

  /**
   * Debugger: Explain what this code does.
   */
  public static boolean doSomethingMysterious(int n) {
    // this function checks to see if a number is a power of 2.
    // if it is a power of 2 then (n-1) will be all 1s that don't overlap, so it will AND to 0.
    // otherwise, it would have at least a 1 still and return false.
    return ((n & (n-1)) == 0);
  }

  /**
   * Conversion: Given two integers, determine the number of bits you would have to flip to convert
   *   one to the other.
   *
   * Assumptions:
   *
   * Time complexity: O(n) where n is the number of bits in the integer, so O(1) for fixed size
   * Space complexity: O(1)
   */
  public static int bitsToConvert(int a, int b) {
    return 0;
  }
}
