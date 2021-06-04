package com.github.myyk.cracking;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
  // return sequence of moves n, Some(true) means right, Some(false) means down. None means no solution.
  // in the grid, true means off limits
  public static boolean[] findPath(boolean[][] grid) {
    if (grid.length == 1 && grid[0].length == 1) {
      return new boolean[0];
    }

    List<List<Optional<Boolean>>> moves = new ArrayList<>();
    for (int i = 0; i < grid.length; i++) {
      List<Optional<Boolean>> next = new ArrayList<>();
      moves.add(next);
      for (int j = 0; j < grid[0].length; j++) {
        next.add(Optional.empty());
      }
    }

    boolean[] solution = findPath(true, 1, 0, grid, moves);
    if (solution != null) {
      return solution;
    }

    solution = findPath(false, 0, 1, grid, moves);
    return solution;
  }

  // moves stores the move to get to the given position
  private static boolean[] findPath(boolean movedRight, int robotX, int robotY, boolean[][] grid, List<List<Optional<Boolean>>> moves) {
    if (robotX >= grid[0].length || robotY >= grid.length) {
      return null;
    } else if (moves.get(robotY).get(robotX).isPresent()) {
      // we've already been here
      return null;
    } else if (grid[robotY][robotX]) {
      return null;
    } else {
      moves.get(robotY).set(robotX, Optional.of(movedRight));
      if (robotX == grid[0].length - 1 && robotY == grid.length - 1) {
        return movesToSolution(moves);
      } else{
        moves.get(robotY).set(robotX, Optional.of(movedRight));
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

  protected static boolean[] movesToSolution(List<List<Optional<Boolean>>> moves) {
    int x = moves.get(0).size() - 1;
    int y = moves.size() - 1;
    boolean[] answer = new boolean[x + y];
    int i = answer.length;
    while (i > 0) {
      i--;
      answer[i] = moves.get(y).get(x).get();
      if (answer[i]) {
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
   * Recursive Multiply: Write a function to multiply two numbers recursively without
   *   using the '*' operator.
   *
   * Assumptions:
   *   all numbers
   *   overflow works the same
   *
   * Time complexity: O(b) where b is the number of bits in the number
   * Space complexity: O(b) from the stacks, though this could be tail call optimized in Scala
   *
   * Difference with book: If you think of b being composed of bits b31 to b0. Then you
   *   can think of product = a*b31*1<<31 + ... + a*b0*1<<0. This is pretty straight
   *   forward to implement and I think less complicated than the book's answer.
   */
  public static int multiply(int a, int b) {
    // optimization by reducing bits on second, maybe, would need to perf test.
    // this could be adding more instructions for all I know.
    if (Integer.highestOneBit(a) > Integer.highestOneBit(b)) {
      return multiplyHelper(a, b, 0, 0);
    } else {
      return multiplyHelper(b, a, 0, 0);
    }
  }

  private static int multiplyHelper(final int a, final int b, final int i, final int product) {
    if (b == 0) {
      return product;
    } else if ((b & 1) == 1) {
      return multiplyHelper(a, b>>>1, i+1, product + (a<<i));
    } else {
      return multiplyHelper(a, b>>>1, i+1, product);
    }
  }

  /**
   * Towers of Hanoi: Given 3 stacks set up as towers of hanoi, move the rings from
   *   one the starting stack to another stack by following the tower of hanoi rules.
   *
   * Assumptions:
   *
   * Time complexity: O(2^n)
   * Space complexity: O(1)
   *
   * Note: I'm just going to do it as written in the book as this is hard for me to
   *   conceptualize since I'm not actually doing any real work.
   * *** Come back to this one.
   */
  public static void moveDisks(int n, Stack<Integer> origin, Stack<Integer> destination, Stack<Integer> buffer) {
    if (n <= 0)  return;
    moveDisks(n-1, origin, buffer, destination);
    moveTop(origin, destination);
    moveDisks(n-1, buffer, destination, origin);
  }

  private static void moveTop(Stack<Integer> origin, Stack<Integer> destination) {
    destination.push(origin.pop());
  }

  /**
   * Permutations without Dups: Compute all permutations of a string of unique chars.
   *
   * Assumptions:
   *
   * Time complexity: O(k!)
   * Space complexity: O(k!)
   *
   * Note: This actually works if there are dups. That was sort of a clever accident.
   *   The issue with the code below though is that StringBuffers apparently don't
   *   hash to the same value if they are equal. This makes sense though since they
   *   are a mutable data structure. Very interesting bug, I'm leaving it so I can see
   *   this again.
   */
  public static Set<String> permutationsUnique(final String str) {
    final Set<StringBuffer> intermediate = permutationsUniqueHelper(str);
    final Set<String> results = Sets.newHashSet();
    for (StringBuffer sb : intermediate) {
      results.add(sb.toString());
    }
    return results;
  }

  private static Set<StringBuffer> permutationsUniqueHelper(final String str) {
    if (str.isEmpty()) {
      return Sets.newHashSet();
    }

    Set<StringBuffer> result = Sets.newHashSet();
    final StringBuffer first = new StringBuffer();
    first.append(str.charAt(0));
    result.add(first);

    for (int i = 1; i < str.length(); i++) {
      final char c = str.charAt(i);
      final Set<StringBuffer> newResult = Sets.newHashSet();
      // This can't handle very large strings cause these Sets aren't doing what you'd
      // normal guess for a String, because StringBuffers don't hash to the same values
      // even when they are equal.
      for (StringBuffer perm : result) {
        final Set<StringBuffer> temp = Sets.newHashSet();
        for (int j = 0; j <= perm.length(); j++) {
          StringBuffer copy = new StringBuffer(perm);
          copy.insert(j, c);
          temp.add(copy);
        }
        newResult.addAll(temp);
      }
      result = newResult;
    }
    return result;
  }
  
  /**
   * Parens: Final all legal pairings of N parenthesis.
   *
   * Assumptions:
   *
   * Time complexity: O(?) // both of these grow quite fast
   * Space complexity: O(?)
   *
   * Difference with book: Their answer requires less memory as it doesn't create a lot
   *   of strings that end up being duplicates and eliminated when adding to the set.
   *   Given that this algorithm hits it's limits at pretty low values of n, I don't
   *   think their optimization is necessary at all. Even for their less optimized
   *   answer and explanation it is more confusing than below in my opinion.
   *
   *   My answer uses 'String.format' to interpolate the strings. It's understandable
   *   what's happening and it's more performant than just '+'-ing the strings.
   *
   *   I also looked at the solution differently, rather than putting parentheses into
   *   the n-1 values, you can think of them as next to or encapsulating the n-1 values.
   *   I find this way of looking at it easier to code correctly.
   */
  public static Set<String> parens(final int n) {
    final Set<String> result = Sets.newHashSet();
    if (n <= 0) {
      // do nothing
    } else if (n == 1) {
      result.add("()");
    } else {
      final Set<String> nMinusOne = parens(n - 1);
      // the one duplicate will be eliminated by the set.
      for (String next : nMinusOne) {
        result.add(String.format("()%s", next));
        result.add(String.format("(%s)", next));
        result.add(String.format("%s()", next));
      }
    }
    return result;
  }

  public static class Color {
    final byte r;
    final byte g;
    final byte b;

    public Color(final byte r, final byte g, final byte b) {
      this.r = r;
      this.g = g;
      this.b = b;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + b;
      result = prime * result + g;
      result = prime * result + r;
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
      Color other = (Color) obj;
      if (b != other.b)
        return false;
      if (g != other.g)
        return false;
      return r == other.r;
    }
  }

  /**
   * Paint Fill: Given an image, color and x,y coordinate fill the color at the
   *   coordinates with the new color and touching pixels with the same old color
   *   and all the pixels touching them and so on.
   *
   * Assumptions:
   *
   * Time complexity: O(n*m) where n and m are the dimensions of the image array
   * Space complexity: O(1)
   */
  public static Color[][] paintFill(final Color[][] image, final int x, final int y, final Color newColor) {
    if (outsideImage(image, x, y)) {
      throw new IllegalArgumentException("x or y is out of range of the image");
    }

    final Color oldColor = image[y][x];
    paintFill(image, x, y, newColor, oldColor);
    return image;
  }

  private static void paintFill(final Color[][] image, final int x, final int y, final Color newColor, final Color oldColor) {
    if (!outsideImage(image, x, y) && image[y][x].equals(oldColor)) {
      image[y][x] = newColor;

      paintFill(image, x+1, y, newColor, oldColor);
      paintFill(image, x-1, y, newColor, oldColor);
      paintFill(image, x, y+1, newColor, oldColor);
      paintFill(image, x, y-1, newColor, oldColor);
    }
  }

  private static boolean outsideImage(final Color[][] image, final int x, final int y) {
    return x < 0 || x >= image[0].length || y < 0 || y >= image.length;
  }

  /**
   * Coins: Given an infinite number of coins of various denominations and a total.
   *   Find the number of different ways to get that total with coins.
   *
   * Assumptions:
   *   positive value coins
   *
   * Time complexity: O(?)
   * Space complexity: O(?)
   *
   * Note: This is a little trickier than doing it in Scala since if you use a List in
   *   the helper function you have to be careful to not mutate the list fucking up
   *   the one of the nested calls since they share the same list.
   *
   *   Also if this needs to be done with large coins to smaller coins.
   */
  public static int coinsCount(final Set<Integer> coins, final int total) {
    Map<Integer, Integer> cache = Maps.newHashMap(); 
    cache.put(0, 1); // base case
    Integer[] coinsArr = new Integer[coins.size()];
    coins.toArray(coinsArr);
    Arrays.sort(coinsArr, Comparator.reverseOrder());
    return coinsCount(coinsArr, 0, total, cache);
  }

  private static int coinsCount(final Integer[] coins, final int index, final int remaining, final Map<Integer, Integer> cache) {
    if (remaining < 0) {
      return 0;
    } else if (cache.containsKey(remaining)) {
      return cache.get(remaining);
    } else if (index == coins.length) {
      return 0;
    } else {
      int ways = 0;
      int coin = coins[index];
      for (int i = 0; i <= remaining; i += coin) {
        ways += coinsCount(coins, index + 1, remaining - i, cache);
      }
      cache.put(remaining, ways);
      return ways;
    }
  }

  /**
   * Eight Queens: Find every combination of boards where you can place 8 queens on
   *   an 8x8 board such that they can't attack each other with standard chess
   *   moves. A result can be expressed as a single array of the row position of the
   *   queen on the i-th column where i is the array index.
   *
   * Assumptions:
   *
   * Time complexity: O(n!) where n is the number of moves, but it's pruned, so maybe
   *   the bound can be expressed tighter than that.
   * Space complexity: O(n!)
   *
   * Notes: Performance could be improved by keeping track of the occupied diagonals and
   *   rows on the way down so that the isBoardValid would be much faster. 
   */
  public static ArrayList<int[]> placeQueens(final int numberOfQueens /* = 8, if we could have defaults*/) {
    return placeQueens(numberOfQueens, 0, new int[numberOfQueens], new ArrayList<>());
  }

  private static ArrayList<int[]> placeQueens(final int numberOfQueens, int queenIndex, int[] placedQueens, ArrayList<int[]> result) {
    if (!isBoardValid(placedQueens, queenIndex, numberOfQueens)) {
      // do nothing
    } else if (queenIndex == numberOfQueens) {
      result.add(placedQueens.clone());
    } else {
      for (int i = 0; i < numberOfQueens; i++) {
        placedQueens[queenIndex] = i;
        placeQueens(numberOfQueens, queenIndex + 1, placedQueens, result);
      }
    }
    return result;
  }

  private static boolean isBoardValid(int[] placedQueens, int numPlacedQueens, int boardSize) {
    boolean[][] board = new boolean[numPlacedQueens][boardSize];
    for (int i = 0; i < numPlacedQueens; i++) {
      board[i][placedQueens[i]] = true;
      for (int j = i-1; j >= 0; j--) {
        int offset = i - j;
        if (board[j][placedQueens[i]]) {
          return false;
        } else if (placedQueens[i] + offset < boardSize && board[j][placedQueens[i] + offset]) {
          // right diagonal
          return false;
        } else if (placedQueens[i] - offset >= 0 && board[j][placedQueens[i] - offset]) {
          return false;
        }
      }
    }
    return true;
  }

  public static class Box {
    public static Box MAX_VALUE = new Box(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public final int height, width, depth;

    public Box(final int height, final int width, final int depth) {
      this.height = height;
      this.width = width;
      this.depth = depth;
    }

    public boolean smallerThan(Box other) {
      return height < other.height && width < other.width && depth < other.depth;
    }
  }

  /**
   * Stack of Boxes: Given a stack of N boxes. Find the maximum height that you can
   *   stack the boxes given the constraints that you cannot rotate a box and a box
   *   can only be stacked if the upper box has smaller width, height, and depth than
   *   the box below it.
   *
   * Assumptions:
   *   boxes have positive values for width, height and depth
   *
   * Time complexity: O()
   * Space complexity: O()
   *
   * Note: I thought of another way you could do this. Though, maybe not any better in
   *   performance would be to construct a directed graph from smaller to larger box.
   *   Then traverse it from each root (smallest) storing at each node the previous
   *   Max(height + previous height, previous max). You could also keep track of the
   *   overall max, so you don't have to search for it later.
   */
  public static int stackHeight(final List<Box> boxes) {
    if (boxes.isEmpty()) {
      return 0;
    }
 
    return stackHeight(Sets.newHashSet(boxes), Box.MAX_VALUE, new HashMap<>());
  }

  private static int stackHeight(final Set<Box> boxes, final Box bottom, final Map<Set<Box>, Integer> cache) {
    if (cache.containsKey(boxes)) {
      return cache.get(boxes);
    } else {
      int maxHeight = 0;
      for (Box box : boxes) {
        if (box.smallerThan(bottom)) {
          final Set<Box> newBoxes = Sets.newHashSet(boxes);
          newBoxes.remove(box);
          maxHeight = Math.max(maxHeight, box.height + stackHeight(newBoxes, box, cache));
        }
      }
      cache.put(boxes, maxHeight);
      return maxHeight;
    }
  }

  /**
   * Boolean Evaluation: Given a boolean expression and a result. Find how many ways
   *   parentheses can be added to the expression to get the result.
   *
   * Assumptions:
   *   cannot put parentheses around a unary expression such as a value 0 or 1
   *   each operator has the same precedence
   *   expressions are valid
   *
   * Time complexity: O()
   * Space complexity: O()
   *
   * Note: The answer is also equal to the Catalan number where n is the number of
   *   operators. That is Cn = (2n)!/((n+1)!n!)
   *
   * Difference with books answer:
   *   They kind of cleverly concatenated the result with the strings for the cache to
   *   use just one. This seems less clean than using a tuple key as I would in Scala
   *   but oh well, this isn't Scala. I got the answer on my own after a while, the
   *   tests really helped. This is one question I didn't write the test first for and
   *   I regret that. It took longer to develop that it could have otherwise.
   *   Calculating the total ways to be able to subtract for the negative case is really
   *   useful. Maybe what I did before was more efficient, but it's doubtful there was
   *   much work saved compared to this answer which is a couple functions smaller and
   *   easier to read.
   */
  public static int countEval(String expression, boolean result) {
    final Map<String, Integer> cacheTrue = Maps.newHashMap();
    cacheTrue.put("0", 0);
    cacheTrue.put("1", 1);
    final Map<String, Integer> cacheFalse = Maps.newHashMap();
    cacheFalse.put("0", 1);
    cacheFalse.put("1", 0);
    return countEval(expression, result, cacheTrue, cacheFalse);
  }

  private static int countEval(String expression, boolean result, Map<String, Integer> cacheTrue, Map<String, Integer> cacheFalse) {
    if (result && cacheTrue.containsKey(expression)) {
      return cacheTrue.get(expression);
    } else if (!result && cacheFalse.containsKey(expression)) {
      return cacheFalse.get(expression);
    } else {
      int count = 0;
      for (int i = 1; i < expression.length(); i+=2) {
        final char c = expression.charAt(i);
        final String left = expression.substring(0, i);
        final String right = expression.substring(i+1);
        final int leftFalse = countEval(left, false, cacheTrue, cacheFalse);
        final int rightFalse = countEval(right, false, cacheTrue, cacheFalse);
        final int leftTrue = countEval(left, true, cacheTrue, cacheFalse);
        final int rightTrue = countEval(right, true, cacheTrue, cacheFalse);
        final int totalWays = (leftFalse + leftTrue) * (rightFalse + rightTrue);

        int ways = 0;
        if (c == '|') {
          ways = (leftFalse*rightTrue) + (leftTrue*rightFalse) + (leftTrue*rightTrue);
        } else if (c == '&') {
          ways = leftTrue * rightTrue;
        } else if (c == '^') {
          ways = (leftTrue * rightFalse) + (leftFalse * rightTrue);
        }
        count += (result ? ways : totalWays - ways);
      }
      if (result) {
        cacheTrue.put(expression, count);
      } else {
        cacheFalse.put(expression, count);
      }
      return count;
    }
  }
}
