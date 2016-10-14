package com.github.myyk.cracking;

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
   * Time complexity: O()
   * Space complexity: O()
   */
  public static int tripleStep(int steps) {
    return tripleStep(steps, Lists.newArrayList(1, 1, 2));
  }

  private static int tripleStep(int steps, List<Integer> stepsToWays) {
    if (steps < 0) {
      return 0;
    } else if (steps < stepsToWays.size()) {
      return stepsToWays.get(steps);
    } else {
      stepsToWays.add(tripleStep(steps - 1) + tripleStep(steps - 2) + tripleStep(steps - 3));
      return stepsToWays.get(steps);
    }
  }
}
