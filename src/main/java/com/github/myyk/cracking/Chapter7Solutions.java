package com.github.myyk.cracking;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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

  public static class Robot {
    private int x;
    private int y;

    public Robot(int x, int y) {
      this.x = x;
      this.y = y;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public void moveRight() {
      x++;
    }

    public void moveDown() {
      y++;
    }
  }

  /**
   * Robot in a Grid: There's a robot and a grid with r rows and c columns. The robot is
   *   in the upper left corner. It can move right or down on each move. There are some
   *   grid locations that are off limits. Design an algorithm to find a path from the
   *   top to the bottom right corner.
   *
   * Assumptions:
   *   grid is NxM
   *
   * Time complexity: O(nm)
   * Space complexity: O(n+m)
   */
  // return sequence of moves n, true means right, false means down. null means no solution.
  // in the grid, true means off limits
  public static boolean[] findPath(boolean[][] grid) {
    if (grid.length == 1 && grid[0].length == 1) {
      return new boolean[0];
    }
    Boolean[][] moves = new Boolean[grid.length][grid[0].length];
    boolean[] solution = findPath(true, 1, 0, grid, moves);
    if (solution != null) {
      return solution;
    }

    solution = findPath(false, 0, 1, grid, moves);
    return solution;
  }

  // moves stores the move to get to the given position
  private static boolean[] findPath(boolean movedRight, int robotX, int robotY, boolean[][] grid, Boolean[][] moves) {
    if (robotX >= grid[0].length || robotY >= grid.length) {
      return null;
    } else if (moves[robotY][robotX] != null) {
      // we've already been here
      return null;
    } else if (grid[robotY][robotX]) {
      return null;
    } else {
      moves[robotY][robotX] = movedRight;
      if (robotX == grid[0].length - 1 && robotY == grid.length - 1) {
        return movesToSolution(moves);
      } else{
        moves[robotY][robotX] = movedRight;
        // try right
        boolean[] solution = findPath(true, robotX + 1, robotY, grid, moves);
        if (solution != null) {
          return solution;
        }
    
        // try left
        solution = findPath(false, robotX, robotY + 1, grid, moves);
        return solution;
      }
    }
  }

  protected static boolean[] movesToSolution(Boolean[][] moves) {
    int x = moves[0].length - 1;
    int y = moves.length - 1;
    boolean[] answer = new boolean[moves[0].length + moves.length - 2];
    int i = answer.length;
    while (i > 0) {
      i--;
      answer[i] = moves[y][x];
      if (moves[y][x]) {
        x--;
      } else {
        y--;
      }
    }

    return answer;
  }

  /**
   * Magic Index: Given an sorted array A[1, 2, ... , n, n-1] find if it has an
   *   index such that A[i] = i.
   *
   * Assumptions:
   *   sorted
   *   distinct
   *
   * Time complexity: O(log n)
   * Space complexity: O(log n)
   */
  // -1 means not found
  public static int findMagicIndexDistinct(final int[] a) {
    if (a == null) {
      return -1;
    }
    return findMagicIndexDistinct(a, 0, a.length - 1);
  }

  private static int findMagicIndexDistinct(final int[] a, int start, int end) {
    int mid = (start + end) / 2;
    if (start > end) {
      return -1;
    } else if (a[mid] == mid) {
      return mid;
    } else if (a[mid] > mid) {
      return findMagicIndexDistinct(a, start, mid - 1);
    } else {
      return findMagicIndexDistinct(a, mid + 1, end);
    }
  }

  /**
   * Magic Index: Given an sorted array A[1, 2, ... , n, n-1] find if it has an
   *   index such that A[i] = i.
   *
   * Assumptions:
   *   sorted
   *
   * Time complexity: O(n)
   * Space complexity: O(log n)
   */
  // -1 means not found
  public static int findMagicIndexNonDistinct(final int[] a) {
    if (a == null) {
      return -1;
    }
    return findMagicIndexNonDistinct(a, 0, a.length - 1);
  }

  private static int findMagicIndexNonDistinct(final int[] a, int start, int end) {
    int mid = (start + end) / 2;
    if (start > end) {
      return -1;
    } else if (a[mid] == mid) {
      return mid;
    } else if (a[mid] < mid) {
      if (a[mid] > 0 && a[mid] < a.length) {
        int answer = findMagicIndexNonDistinct(a, start, a[mid]);
        if (answer != -1) {
          return answer;
        }
      }
      return findMagicIndexNonDistinct(a, mid + 1, end);
    } else {
      if (a[mid] > 0 && a[mid] < a.length) {
        int answer = findMagicIndexNonDistinct(a, a[mid], end);
        if (answer != -1) {
          return answer;
        }
      }
      return findMagicIndexNonDistinct(a, start, mid -1);
    }
  }

  /**
   * Power Set: Write a function to return all subsets of a set.
   *
   * Assumptions:
   *
   * Time complexity: O(2^n)
   * Space complexity: O(2^n)
   */
  public static <T> Set<Set<T>> powerSet(Set<T> set) {
    Set<Set<T>> result = Sets.newHashSet();
    return powerSet(set, result);
  }

  private static <T> Set<Set<T>> powerSet(Set<T> set, Set<Set<T>> result) {
    if (!result.contains(set)) {
      result.add(set);
      for (T next : set) {
        Set<T> newSet = Sets.newHashSet(set);
        newSet.remove(next);
        powerSet(newSet, result);
      }
    }
    return result;
  }

  /**
   * Recursive Multiply: Write a fuction to multiply two numbers recursively without
   *   using the '*' operator.
   *
   * Assumptions:
   *   all numbers
   *   overflow works the same
   *
   * Time complexity: O(2^n)
   * Space complexity: O(2^n)
   */
  public static int multiply(int a, int b) {
    return 0;
  }
}
