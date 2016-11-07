package com.github.myyk.cracking;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Hard
 */
public class Chapter17Solutions {

  /**
   * Add Without Plus: Do addition without the '+' operator.
   *
   * Assumptions:
   *
   * Time complexity: O(b) where b is the number of bits in the numbers
   * Space complexity: O(1)
   *
   * Note: this is ugly and can be improved
   */
  public static int addWithoutPlus(int a, int b) {
    int sum = 0;
    boolean carry = false;
    for (int i = 0; i < 32; i++) {
      int mask = 1<<i;
      int x = a & mask;
      int y = b & mask;
      if (x != 0 && y != 0) {
        if (carry) {
          sum |= mask;
        }
        carry = true;
      } else if (x != y) {
        if (!carry) {
          carry = false;
          sum |= mask;
        }
      } else {
        if (carry) {
          sum |= mask;
          carry = false;
        }
      }
    }
    return sum;
  }

  private static final Random random = new Random();

  /**
   * Shuffle: Shuffle an array of cards with equal probability.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static <T> void shuffle(T[] cards) {
    for (int i = cards.length; i > 0; i--) {
      int swapIndex = random.nextInt(i);
      T temp = cards[i - 1];
      cards[i - 1] = cards[swapIndex];
      cards[swapIndex] = temp;
    }
  }

  /**
   * Random Set: Given an array of integers, generate a random set of size m with equal probability of
   *   having each value from the array in the resulting set.
   *
   * Assumptions:
   *   no duplicates in the array
   *   can destroy the array
   *
   * Time complexity: O(m) where m is the size of the set.
   * Space complexity: O(m)
   *
   * Difference with book: They didn't stick with returning a 'set' so didn't need to worry about uniqueness.
   *   Though, my assumptions dealt with that. Still, their solution is O(v) where v is the number of values
   *   in the input array, while mine is simply O(m) which is better especially if m is much smaller than
   *   v.
   */
  public static Set<Integer> randomSet(final int[] values, final int m) {
    if (values.length < m) {
      throw new IllegalArgumentException("Can't create set of size m from values.");
    }

    final Set<Integer> result = Sets.newHashSet();

    int i = values.length;
    for (int selected = 0; selected < m; selected++) {
      final int swapIndex = random.nextInt(i);
      result.add(values[swapIndex]);
      values[swapIndex] = values[i - 1];
      i--;
    }
    return result;
  }

  /**
   * Missing Number: Given an array of integers with values 0 to N and one missing integer find the
   *   missing integer. The array is non-standard, you cannot access a full integer in a single operation
   *   you can only access a bit at a time in constant time.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   *
   * Difference with book: If you can't read more than a bit, doesn't make much sense you can move the
   *   full integer at once. They use references to integers, where I'm not.
   */
  public static int missingNumber(final int[] array) {
    List<Integer> indices = Lists.newArrayList();
    for (int i = 0; i < array.length; i++) {
      indices.add(i);
    }
    return missingNumber(indices, array, 0);
  }

  private static int missingNumber(final List<Integer> indices, final int[] array, int position) {
    if (indices.isEmpty()) {
      return 0;
    }

    final List<Integer> evens = Lists.newArrayListWithCapacity(indices.size()/2 + 1);
    final List<Integer> odds = Lists.newArrayListWithCapacity(indices.size()/2 + 1);
    for (Integer i : indices) {
      if (getBit(array, i, position) == 0) {
        evens.add(i);
      } else {
        odds.add(i);
      }
    }

    if (evens.size() <= odds.size()) {
      int v = missingNumber(evens, array, position + 1);
      return (v << 1);
    } else {
      int v = missingNumber(odds, array, position + 1);
      return (v << 1) | 1;
    }
  }

  private static int getBit(final int[] array, final int i, final int j) {
    return (array[i] >> j) & 1;
  }

  /**
   * Letters and Numbers: Given an array of letters and numbers, find the longest subarray with an equal
   *   number of letters and numbers.
   *
   * Assumptions:
   *   only letters and numbers in array
   *
   * Time complexity: O()
   * Space complexity: O()
   */
  public static char[] findLongestSubArrayWithEqualLettersAndNumbers(final char[] chars) {
    int longestStart = 0;
    int longestEnd = 0;
    for (int i = 0; i < chars.length; i++) {
      for (int j = i + 1; j <= chars.length; j++) {
        if (longestEnd - longestStart < j - i && isBalanced(chars, i, j)) {
          longestStart = i;
          longestEnd = j;
        }
      }
    }
    if (longestStart == longestEnd) {
      return new char[0];
    } else {
      return Arrays.copyOfRange(chars, longestStart, longestEnd);
    }
  }

  private static boolean isBalanced(final char[] chars, final int from, final int to) {
    int balance = 0;
    for (int i = from; i < to; i++) {
      if (Character.isLetter(chars[i])) {
        balance += 1;
      } else {
        balance -= 1;
      }
    }
    return (balance == 0);
  }
}
