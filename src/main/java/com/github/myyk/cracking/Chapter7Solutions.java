package com.github.myyk.cracking;

import java.math.BigInteger;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * Recursion and Dynamic Programming
 */
public class Chapter7Solutions {
  /**
   * Triple Step: A child is running up steps, they can take 1, 2, or 3 steps at a time. How many ways
   *   can they make it up N steps?
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   */
  public static BigInteger tripleStepRecursive(final int steps) {
    return tripleStepRecursive(steps, Lists.newArrayList(BigInteger.valueOf(1), BigInteger.valueOf(1), BigInteger.valueOf(2)));
  }

  private static BigInteger tripleStepRecursive(final int steps, final List<BigInteger> stepsToWays) {
    System.out.println("steps = " + steps + "    stepsToWays = " + stepsToWays.toString());
    if (steps < 0) {
      return BigInteger.ZERO;
    } else if (steps < stepsToWays.size()) {
      return stepsToWays.get(steps);
    } else {
      BigInteger ways = tripleStepRecursive(steps - 1, stepsToWays).add(tripleStepRecursive(steps - 2, stepsToWays).add(tripleStepRecursive(steps - 3, stepsToWays)));
      stepsToWays.add(ways);
      return ways;
    }
  }

  /**
   * Triple Step: Same as above but using less space and coded iteratively.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static BigInteger tripleStepIterative(int steps) {
    if (steps < 0) {
      return BigInteger.ZERO;
    } else if (steps == 0 || steps == 1) {
      return BigInteger.ONE;
    } else if (steps == 2) {
      return BigInteger.valueOf(2);
    } else {
      BigInteger nMinus3 = BigInteger.ONE;
      BigInteger nMinus2 = BigInteger.ONE;
      BigInteger nMinus1 = BigInteger.valueOf(2);

      for (int i = 3; i < steps; i++) {
        BigInteger temp = nMinus1.add(nMinus2.add(nMinus3));
        nMinus3 = nMinus2;
        nMinus2 = nMinus1;
        nMinus1 = temp;
      }
      return nMinus1.add(nMinus2.add(nMinus3));
    }
  }
}
