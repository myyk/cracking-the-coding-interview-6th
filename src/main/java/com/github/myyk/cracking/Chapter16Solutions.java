package com.github.myyk.cracking;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;

import com.github.myyk.cracking.Chapter16Solutions.AntGrid.AntGridResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import scala.Tuple2;

/**
 * Moderate
 */
public class Chapter16Solutions {
  public static class MutableInteger {
    public int value;

    public MutableInteger(int value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /**
   * Check Permutation: Given 2 string check to see if one is a permutation of the other.
   *
   * Assumptions:
   *    ASCII characters
   *    capitalization matters
   *    whitespace matters
   *    strings aren't larger than 2^16
   *
   * Time complexity: O(max(c,n) where c is number of characters in alphabet (128)
   * and n is the length of the string.
   * Space complexity: O(c) where c is number of characters in alphabet (128). Or O(1) technically.
   */
  public static void swapInPlace(MutableInteger a, MutableInteger b) {
    a.value ^= b.value;
    b.value ^= a.value;
    a.value ^= b.value;
  }

  /**
   * Word Frequencies: Given a book find the frequencies of a word in it. What if you had to do this
   *   multiple times for different words in the book?
   *
   * Assumptions:
   *   case insensitive matching
   *   ignores punctuation
   *   unicode characters are ok
   *   valid words for input
   *
   * Time complexity: O(n) first time, O(1) there after or O(s) where s is the number of characters in the input string.
   * Space complexity: O(n)
   *
   * Note: One could use a MultiMap<String> instead of a Map<String, Integer> since this is what it's for.
   */
  public static class WordFrequencies {
    private final Map<String, Integer> frequencies;

    public WordFrequencies(final String text) {
      frequencies = createFrequencyMap(text);
    }

    private static Map<String, Integer> createFrequencyMap(final String text) {
      final Map<String, Integer> frequencies = Maps.newHashMap();
      for (String word : text.split(" ")) {
        word = word.replaceAll("[^\\p{L}]", "");
        if (!word.isEmpty()) {
          frequencies.put(word, getFrequency(frequencies, word) + 1);
        }
      }
      return frequencies;
    }

    public int getFrequency(final String word) {
      return getFrequency(frequencies, word);
    }

    private static int getFrequency(Map<String, Integer> frequencies, final String word) {
      return frequencies.getOrDefault(word.toLowerCase(), 0);
    }
  }

  public static class TicTacToe {
    public static final TicTacToe X = new TicTacToe();
    public static final TicTacToe O = new TicTacToe();
    private TicTacToe() {}
    public static TicTacToe[][] newBoard() {
      return new TicTacToe[3][3];
    }
  }

  /**
   * Tic Tac Toe: Determine if someone has won a game of tic tac toe.
   *
   * Assumptions:
   *   is a valid board (NxN)
   *   work for an NxN game of tic tac toe
   *   we haven't been checking the board after each move
   *
   * Time complexity: O(n*n) could need to check every position
   * Space complexity: O(1)
   *
   * Note: I like to answer this in Scala the most since you just use pattern matching for a really
   *   understandably correct answer.
   *   Easy to modify this for NxN board. Would need to work on the diagonal a bit more.
   *
   * Difference with book: I think because I've seen this before too many times, I didn't have my
   *   beginners eyes on. There's an interesting answer of converting each board to a number and storing
   *   that in a lookup. This would be useful if doing this many many times possibly.
   */
  public static boolean isWonTicTacToe(final TicTacToe[][] board) {
    final int N = board.length;
    // check across
    for (TicTacToe[] ticTacToes : board) {
      final TicTacToe next = ticTacToes[0];
      if (next != null) {
        for (int j = 1; j < N; j++) {
          if (ticTacToes[j] != next) {
            break;
          } else if (j == N - 1) {
            return true;
          }
        }
      }
    }
    // check down
    for (int i = 0; i < N; i++) {
      final TicTacToe next = board[0][i];
      if (next != null) {
        for (int j = 1; j < N; j++) {
          if (board[j][i] != next) {
            break;
          } else if (j == N-1) {
            return true;
          }
        }
      }
    }

    // check diagonals
    TicTacToe next = board[0][0];
    if (next != null) {
      for (int i = 1; i < N; i++) {
        if (board[i][i] != next) {
          break;
        } else if (i == N-1) {
          return true;
        }
      }
    }
    next = board[N-1][0];
    if (next != null) {
      for (int i = 1; i < N; i++) {
        if (board[N-1-i][i] != next) {
          break;
        } else if (i == N-1) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Factorial Zeroes: Figure out how many 0s are at the end of a factorial.
   *
   * Assumptions:
   *
   * Time complexity: O(n/5)
   * Space complexity: O(1)
   */
  public static int factorialZeroes(int n) {
    // in other words, how many factors of 2 and 5.
    // it should be obvious that there will always be more factors 2s than 5 since every other number is
    // a factor of 2, while only ever 5 are factors of 5
    int factorsOf5 = 0;
    for (int i = 5; n / i > 0; i*=5) {
      factorsOf5 += n / i;
    }
    return factorsOf5;
  }

  /**
   * Smallest Difference: Given two arrays of integers find the two values between them that have the
   *   smallest difference.
   *
   * Assumptions:
   *   not sorted
   *   exception for invalid input
   *   it's okay to sort the inputs (otherwise, we'd just have to copy)
   *
   * Time complexity: O(n log n)
   * Space complexity: O(1) // depending on the sort being used
   */
  public static int smallestDifference(final int[] a, final int[] b) {
    if (a.length == 0 || b.length == 0) {
      throw new IllegalArgumentException("Cannot compute if one or more arrays are empty.");
    }

    Arrays.sort(a);
    Arrays.sort(b);
    int diff = Integer.MAX_VALUE;
    for (int i = 0, j = 0; i < a.length && j < b.length && diff != 0;) {
      if (a[i] < b[j]) {
        diff = Math.min(diff, b[j] - a[i]);
        i++;
      } else {
        diff = Math.min(diff, a[i] - b[j]);
        j++;
      }
    }
    return diff;
  }

  /**
   * Number Max: Find the maximum between two numbers without using any comparators.
   *
   * Assumptions:
   *
   * Time complexity: O(1)
   * Space complexity: O(1)
   *
   * Note: Followed the book very closely in writing this.
   */
  public static int numberMax(final int a, final int b) {
    int c = a-b;

    int signA = getSign(a); // 1 if a >= 0, else 0
    int signB = getSign(b); // 1 if b >= 0, else 0
    int signC = getSign(c); // depends on whether a-b overflowed
    // 1 if a > b and 0 otherwise if no overflow
    // 0 if a < b and 0 otherwise if overflow

    int aAndBDifferentSigns = signA ^ signB;
    int aAndBSameSigns = flip(aAndBDifferentSigns);

    int k = aAndBDifferentSigns*signA + aAndBSameSigns*signC;

    return a*k | b*flip(k);
  }

  // 1 for positive, 0 for negative
  private static int getSign(int n) {
    return flip(n>>31 & 0x1);
  }

  // flips a 0 to a 1 and a 1 to a 0
  private static int flip(int bit) {
    return 1^bit;
  }

  /**
   * English Int: Given a number, convert it to the English word representation.
   *
   * Assumptions:
   *
   * Time complexity: O()
   * Space complexity: O()
   */
  public static class EnglishIntMaker {
    private static final Map<Integer, String> lookup = Maps.newHashMap();
    static {{
      lookup.put(1, "One");
      lookup.put(2, "Two");
      lookup.put(3, "Three");
      lookup.put(4, "Four");
      lookup.put(5, "Five");
      lookup.put(6, "Six");
      lookup.put(7, "Seven");
      lookup.put(8, "Eight");
      lookup.put(9, "Nine");
      lookup.put(10, "Ten");
      lookup.put(11, "Eleven");
      lookup.put(12, "Twelve");
      lookup.put(13, "Thirteen");
      lookup.put(14, "Fourteen");
      lookup.put(15, "Fifteen");
      lookup.put(16, "Sixteen");
      lookup.put(17, "Seventeen");
      lookup.put(18, "Eighteen");
      lookup.put(19, "Nineteen");
      lookup.put(20, "Twenty");
      lookup.put(30, "Thirty");
      lookup.put(40, "Forty");
      lookup.put(50, "Fifty");
      lookup.put(60, "Sixty");
      lookup.put(70, "Seventy");
      lookup.put(80, "Eighty");
      lookup.put(90, "Ninety");
    }}

    public String englishInt(final int num) {
      StringBuffer sb = new StringBuffer();
      if (num == 0) {
        return "Zero";
      } else if (num == Integer.MIN_VALUE) {
        return "Negative Two Billion, One Hundred Forty Seven Million, Four Hundred Eighty Three Thousand, Six Hundred Forty Eight";
      } else if (num < 0) {
        sb.append("Negative ");
        englishInt(-num, sb);
      } else {
        englishInt(num, sb);
      }
      return sb.toString();
    }

    private void englishInt(final int num, final StringBuffer sb) {
      if (lookup.containsKey(num)) {
        sb.append(lookup.get(num));
      } else if (num > 20 && num < 100) {
        final int onesDigit = num%10;
        englishInt(num - onesDigit, sb);
        sb.append(" ");
        englishInt(onesDigit, sb);
      } else if (num >= 100 && num < 1000) {
        englishInt(num, "Hundred", 100, "", sb);
      } else if (num >= 1000 && num < 1000000) {
        englishInt(num, "Thousand", 1000, ",", sb);        
      } else if (num >= 1000000 && num < 1000000000) {
        englishInt(num, "Million", 1000000, ",", sb);        
      } else if (num >= 1000000000) {
        englishInt(num, "Billion", 1000000000, ",", sb);        
      } else {
        throw new RuntimeException("I'm not sure what happened but num is = " + num + " and sb = " + sb.toString());
      }
    }

    private void englishInt(final int num, final String scaleStr, final int scale, final String delimiter, final StringBuffer sb) {
      final int numOfScale = num / scale;
      final int remainder = num % scale;
      englishInt(numOfScale, sb);
      sb.append(" ");
      sb.append(scaleStr);
      if (remainder > 0) {
        sb.append(delimiter);
        sb.append(" ");
        englishInt(remainder, sb);
      }
    }
  }

  /**
   * Operations: Subtract, multiply, and divide all using only add operator.
   *
   * Assumptions:
   *
   * Time complexity: O(n) where n is 2^32
   * Space complexity: O(1)
   */
  public static int subtract(int a, int b) {
    return a + negate(b);
  }
 /**
  * Time complexity: O(log(n)^2)
  * Space complexity: O(1)
  */
  private static int negate(final int n) {
    int newSign = n > 0 ? -1 : 1;
    int delta = newSign;
    int result = 0;
    int count = n;
    while (count != 0) {
      boolean signsChange = (count + delta > 0) != (n > 0);
      if (signsChange && count+delta != 0) {
        delta = newSign;
      }
      count += delta;
      result += delta;
      delta += delta;
    }
    return result;
  }

  // will not complete for very large numbers
  public static int multiply(final int a, final int b) {
    if (abs(a) < abs(b)) {
      return multiply(b, a);
    }

    int product = 0;
    for (int i = 0; i < abs(b); i++) {
      product += a;
    }
    if (b < 0) {
      return negate(product);
    } else {
      return product;
    }
  }

  private static int abs(int n) {
    if (n >= 0) {
      return n;
    } else {
      return negate(n);
    }
  }

  // will not complete for very large numbers
  public static int divide(int a, int b) {
    if (b == 0) {
      throw new ArithmeticException("Cannot divide by zero");
    }

    int quotient = 0;
    int x = 0;
    int absA = abs(a);
    int absB = abs(b);
    while (x + absB <= absA) {
      quotient++;
      x = x + absB;
    }
    if (a < 0 != b < 0) {
      return negate(quotient);
    } else {
      return quotient;
    }
  }

  public static class Person {
    final int birthYear;
    final int deathYear;

    public Person(final int birthYear, final int deathYear) {
      this.birthYear = birthYear;
      this.deathYear = deathYear;
    }
  }

  /**
   * Living People: Given a bunch of people's birth and death year, find the year where
   *   the most people were alive.
   *
   * Assumptions:
   *   count the birth and death year as years the person was alive
   *   people were born between 1900 and 2000 inclusive
   *   Assume people live no more than 200 years
   *
   * Time complexity: O(max(n, y)) where n is the number of people and y is number of years
   * Space complexity: O(y) where y is the valid number of years people lived
   */
  public static int livingPeople(Set<Person> people) {
    final int[] births = new int[300];
    final int[] deaths = new int[300];
    for (Person person : people) {
      births[person.birthYear-1900]++;
      deaths[person.deathYear-1900]++;
    }

    int maxPop = 0;
    int population = 0;
    int yearOffset = 0;
    for (int i = 0; i < 300; i++) {
      population += births[i];
      if (maxPop < population) {
        maxPop = population;
        yearOffset = i;
      }
      population -= deaths[i];
    }
    return 1900 + yearOffset;
  }

  /**
   * This can be used to check my answer.
   */
  public static int livingPeopleBruteForce(final Set<Person> people) {
    final int[] populations = new int[300];
    for (Person person : people) {
      for (int i = person.birthYear - 1900; i <= person.deathYear - 1900; i++) {
        populations[i]++;
      }
    }
    int maxPop = 0;
    int yearOffset = 0;
    for (int i = 0; i < populations.length; i++) {
      if (maxPop < populations[i]) {
        maxPop = populations[i];
        yearOffset = i;
      }
    }
    return 1900 + yearOffset;
  }

  /**
   * Diving Board: How many different sized diving board can be built from k planks
   *   of size 'smaller' and 'larger'?
   *
   * Assumptions:
   *
   * Time complexity: O()
   * Space complexity: O()
   *
   * Note: I found this when writing some test cases and computing it by hand. It's
   *   pretty simple. The smallest board is k of the smallest piece. The largest is k
   *   of the largest piece. Any other unique length can be found by removing a board
   *   of the smaller length and adding the longer one. That would add length of the
   *   larger - smaller to the previous length.
   */
  public static Set<Integer> countDivingBoardsOfKPieces(final int smaller, final int larger, final int k) {
    final Set<Integer> boards = Sets.newHashSet();
    if (k > 0) {
      boards.add(larger*k);
      for (int i = smaller*k; i < larger*k; i += (larger - smaller)) {
        boards.add(i);
      }
    }
    return boards;
  }

  /**
   * Modified Diving Board: How many ways can a diving board be built with planks of size
   *   'smaller' and 'larger' where the board is of size k?
   *
   * Assumptions:
   *
   * Time complexity: O(k)
   * Space complexity: O(1)
   */
  public static int countDivingBoardsOfSize(final int smaller, final int larger, final int k) {
    if (smaller > larger) {
      throw new IllegalArgumentException("Smaller cannot be larger than larger.");
    }

    return countDivingBoardsOfSize(smaller, larger, k, Maps.newHashMap());
  }

  private static int countDivingBoardsOfSize(final int smaller, final int larger, final int k, final Map<Integer, Integer> memo) {
    if (smaller > larger) {
      throw new IllegalArgumentException("Smaller cannot be larger than larger.");
    } else if (k == 0) {
      return 1;
    } else if (k < 0) {
      return 0;
    } else if (larger == 0) {
      return 0;
    } else if (memo.containsKey(k)) {
      return memo.get(k);
    } else {
      int boardRemaining = k;
      int count = 0;
      while (boardRemaining >= 0) {
        count += countDivingBoardsOfSize(0, smaller, boardRemaining);
        boardRemaining -= larger;
      }
      memo.put(k, count);
      return count; 
    }
  }

  public static class Line {
    public static final double epsilon = 0.0001;

    // y intercept unless infiniteSlope == true, then x intercept
    public final double intercept;
    public final double slope;
    public final boolean infiniteSlope;

    public Line(double yIntercept, double slope) {
      this.intercept = floorDouble(yIntercept);
      this.slope = floorDouble(slope);
      this.infiniteSlope = false;
    }

    // use the yIntercept as an xIntercept in this case.
    public Line(double intercept) {
      this.intercept = floorDouble(intercept);
      this.slope = 0;
      this.infiniteSlope = true;
    }

    public Line(final Point a, final Point b) {
      if (isEquivalent(a.x, b.x)) {
        this.intercept = floorDouble(a.x);
        this.slope = 0;
        this.infiniteSlope = true;
      } else {
        this.slope = floorDouble((a.y - b.y) / (a.x - b.x));
        this.intercept = floorDouble(a.y - this.slope * a.x); // y = mx + b
        this.infiniteSlope = false;
      }
    }

    private static double floorDouble(double n) {
      int temp = (int) (n / epsilon);
      return ((double) temp) * epsilon;
    }

    private static boolean isEquivalent(double a, double b) {
      return Math.abs(a - b) < epsilon;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (infiniteSlope ? 1231 : 1237);
      long temp;
      temp = Double.doubleToLongBits(intercept);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(slope);
      result = prime * result + (int) (temp ^ (temp >>> 32));
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
      Line other = (Line) obj;
      if (infiniteSlope != other.infiniteSlope)
        return false;
      if (!isEquivalent(intercept, other.intercept))
        return false;
      return isEquivalent(slope, other.slope);
    }

    @Override
    public String toString() {
      return "Line [intercept=" + intercept + ", slope=" + slope
          + ", infiniteSlope=" + infiniteSlope + "]";
    }
  }

  public static class Point {
    final public double x;
    final public double y;

    public Point(double x, double y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      long temp;
      temp = Double.doubleToLongBits(x);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(y);
      result = prime * result + (int) (temp ^ (temp >>> 32));
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
      Point other = (Point) obj;
      if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
        return false;
      return Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
    }
  }

  /**
   * Best Line: Given a set of points in a 2-D space, find a like that passes through the most points.
   *
   * Assumptions:
   *   at least 1 point
   *
   * Time complexity: O(n*n)
   * Space complexity: O(n*n)
   *
   * Note: I think this performance could be improved by skipping points known to be on
   *  the line from checking a previous point on the line. We already have these in
   *  lines, but modifying the pointsToCheck while iterating over it gives us a
   *  concurrent modification error.
   */
  public static Line bestLine(final Set<Point> points) {
    if (points.size() == 1) {
      return new Line(new Point(0, 0), points.iterator().next());
    } else {
      Line bestLineSoFar = null;
      int maxSetSize = 0;
      final Map<Line, Set<Point>> lines = Maps.newHashMap();
      final Set<Point> allPoints = Sets.newHashSet(points);
      final Iterator<Point> it = allPoints.iterator();
      Point a;
      while (it.hasNext()) {
        a = it.next();
        it.remove();
        final Set<Point> pointsToCheck = Sets.newHashSet(allPoints);
        for (Point b : pointsToCheck) {
          final Line line = new Line(a, b);
          Set<Point> pointsOnLine;
          if (lines.containsKey(line)) {
            pointsOnLine = lines.get(line);
            pointsOnLine.add(b);
          } else {
            pointsOnLine = Sets.newHashSet(a, b);
            lines.put(line, pointsOnLine);
          }

          if (pointsOnLine.size() > maxSetSize) {
            maxSetSize = pointsOnLine.size();
            bestLineSoFar = line;
          }
        }
      }
      return bestLineSoFar;
    }
  }

  public static class MasterMindResult {
    public final int hits;
    public final int psuedoHits;

    public MasterMindResult(int hits, int pseudoHits) {
      this.hits = hits;
      this.psuedoHits = pseudoHits;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + hits;
      result = prime * result + psuedoHits;
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
      MasterMindResult other = (MasterMindResult) obj;
      if (hits != other.hits)
        return false;
      return psuedoHits == other.psuedoHits;
    }

    @Override
    public String toString() {
      return "MasterMindResult [hits=" + hits + ", psuedoHits=" + psuedoHits
          + "]";
    }
  }

  /**
   * Master Mind: Write a function to compute the score of a round of Master Mind.
   *
   * Assumptions:
   *   input is valid: solution and guess same length, only valid characters
   *
   * Time complexity: O(k) where k is length of solution
   * Space complexity: O(c) where c is the number of characters possible
   */
  public static MasterMindResult masterMindScore(String solution, String guess) {
    final Set<Character> solutionChars = stringToCharSet(solution);
    final Set<Character> guessChars = stringToCharSet(guess);
    final Set<Character> charsInBoth = Sets.intersection(solutionChars, guessChars);
    final Tuple2<Integer, Set<Character>> hits = findHits(solution, guess);
    final Set<Character> charsInPsuedoHit = Sets.difference(charsInBoth, hits._2);
    return new MasterMindResult(hits._1, charsInPsuedoHit.size());
  }

  private static Set<Character> stringToCharSet(String guess) {
    final Set<Character> set = Sets.newHashSet();
    for (int i = 0; i < guess.length(); i++) {
      set.add(guess.charAt(i));
    }
    return set;
  }

  private static Tuple2<Integer, Set<Character>> findHits(String solution, String guess) {
    final Set<Character> hitsChar = Sets.newHashSet();
    int hitsCount = 0;
    for (int i = 0; i < solution.length(); i++) {
      if (solution.charAt(i) == guess.charAt(i)) {
        hitsChar.add(solution.charAt(i));
        hitsCount++;
      }
    }
    return new Tuple2<>(hitsCount, hitsChar);
  }

  /**
   * Master Mind: Write a function to compute the score of a round of Master Mind.
   *
   * Assumptions:
   *   input is valid: solution and guess same length, only valid characters
   *
   * Time complexity: O(k) where k is length of solution
   * Space complexity: O(c) where c is the number of characters possible
   *
   * Note: Wrote this as one function as a comparison.
   */
  public static MasterMindResult masterMindScore2(String solution, String guess) {
    final Set<Character> seenInHits = Sets.newHashSet();
    final Set<Character> guesses  = Sets.newHashSet();
    final Set<Character> seenInSolution  = Sets.newHashSet();
    int hits = 0;

    for (int i = 0; i < solution.length(); i++) {
      char nextSolution = solution.charAt(i);
      char nextGuess = guess.charAt(i);
      guesses.add(nextGuess);
      seenInSolution.add(nextSolution);
      if (nextSolution == nextGuess) {
        hits++;
        seenInHits.add(nextSolution);
      }
    }

    final Set<Character> psuedoHits  = Sets.difference(Sets.intersection(guesses, seenInSolution), seenInHits);
    return new MasterMindResult(hits, psuedoHits.size());
  }

  /**
   * Sub Sort: Given an integer array that is unsorted, give the indexes n and m such
   *   that sorting the sub-array from n to m would give a sorted array.
   *
   * Assumptions:
   *   if unsorted length = 1 is ok
   *   indices are inclusive
   *   array is not empty
   *
   * Time complexity: O(n) where n is the size of the array
   * Space complexity: O(1)
   *
   * Difference with book: I like my solution better than the book's. Mine iterates
   *   through the array twice instead of 4 times. I also find mine a bit easier to
   *   read at a little over half as many lines of code and less loops.
   */
  public static Tuple2<Integer, Integer> subSortIndexes(final int[] array) {
    final int firstUnsorted = findFirstUnsortedIndex(array);
    final int lastUnsorted = findLastUnsortedIndex(array);
    return new Tuple2<>(firstUnsorted, lastUnsorted);
  }

  private static int findFirstUnsortedIndex(final int[] array) {
    int firstUnsorted = array.length - 1;
    int minimum = array[firstUnsorted];
  
    for (int i = array.length - 2; i >= 0; i--) {
      if (array[i] > minimum) {
        firstUnsorted = i;
      }
      minimum = Math.min(array[i], minimum);
    }
    return firstUnsorted;
  }

  private static int findLastUnsortedIndex(int[] array) {
    int lastUnsorted = 0;
    int maximum = array[lastUnsorted];
  
    for (int i = 1; i < array.length; i++) {
      if (array[i] < maximum) {
        lastUnsorted = i;
      }
      maximum = Math.max(array[i], maximum);
    }
    return lastUnsorted;
  }

  /**
   * Contiguous Sequence: Given an array of integers (positive and negative) find the
   *   contiguous sequence with the highest sum and return the sum.
   *
   * Assumptions:
   *
   * Time complexity: O(n*n) where n is the size of the array
   * Space complexity: O(n*n)
   *
   * Note: Dynamic Programming isn't really the best way to do this after reading the
   *   book's answer. See maxContiguousSequenceSum2 for a faster answer.
   */
  public static int maxContiguousSequenceSum(final int[] array) {
    return maxContiguousSequenceSum(array, 0, array.length-1, new Integer[array.length][array.length], new Integer[array.length][array.length]);
  }

  private static int maxContiguousSequenceSum(final int[] array, final int i, final int j, final Integer[][] memo, final Integer[][] sums) {
    if (i == j) {
      return array[i];
    } else if (i > j) {
      return Integer.MIN_VALUE;
    } else if (memo[i][j] != null) {
      return memo[i][j];
    } else {
      int max = sumSubArray(array, i, j, sums);
      for (int k = i; k < j; k++) {
        max = Math.max(max, maxContiguousSequenceSum(array, i, k, memo, sums));
        max = Math.max(max, maxContiguousSequenceSum(array, k+1, j, memo, sums));
      }
      memo[i][j] = max;
      return max;
    }
  }

  private static int sumSubArray(final int[] array, final int i, final int j, final Integer[][] sums) {
    if (i == j) {
      return array[i];
    } else if (i > j) {
      return 0;
    } else if (sums[i][j] != null) {
      return sums[i][j];
    } else {
      sums[i][j] = array[i] + sumSubArray(array, i+1, j, sums);
      return sums[i][j];
    }
  }

  /**
   * Contiguous Sequence: Given an array of integers (positive and negative) find the
   *   contiguous sequence with the highest sum and return the sum.
   *
   * Assumptions:
   *
   * Time complexity: O(n) where n is the size of the array
   * Space complexity: O(1)
   *
   * Difference with book: I didn't like their choice of all negative arrays returning
   *   0, it should be as the question states IMO.
   */
  public static int maxContiguousSequenceSum2(final int[] array) {
    if (array == null || array.length == 0) {
      throw new IllegalArgumentException("array must be non-empty non-null");
    }
    int max = array[0];
    int currentSum = max;
    for (int i = 1; i < array.length; i++) {
      if (currentSum + array[i] > 0) {
        currentSum += array[i];
      } else {
        currentSum = array[i];
      }
      max = Math.max(max, currentSum);
    }
    return max;
  }

  /**
   * Pattern Matching: Given a pattern and a value, see if the pattern can match the
   *   value. Each character in the pattern needs to be uniquely mapped to a
   *   sequence of characters.
   *
   * Assumptions:
   *
   * Time complexity: O() // not very good because of all the 'substring's as coded
   * Space complexity: O()
   *
   * Difference with book: I modified this to allow patterns to have many
   *   different characters instead of just 'a' and 'b'.
   */
  public static boolean doesPatternMatch(final String pattern, final String value) {
    if (pattern.isEmpty()) {
      return false;
    }

    return doesPatternMatch(pattern, value, Maps.newHashMap());
  }

  private static boolean doesPatternMatch(final String pattern, final String value, final Map<Character, String> mappings) {
    if (pattern.isEmpty() && value.isEmpty()) {
      return true;
    } else if (pattern.isEmpty() || value.isEmpty()) {
      return false;
    }

    char next = pattern.charAt(0);
    if (mappings.containsKey(next)) {
      String prefix = mappings.get(next);
      if (!value.startsWith(prefix)) {
        // this mapping is wrong
        return false;
      } else {
        return doesPatternMatch(pattern.substring(1), value.substring(prefix.length()), mappings);
      }
    } else {
      for (int i = value.length(); i >= 1; i--) {
        String nextPrefix = value.substring(0, i);
        if (!mappings.containsValue(nextPrefix)) { //TODO: make O(n) by using a bi-map
          mappings.put(next, nextPrefix);
          if (doesPatternMatch(pattern.substring(1), value.substring(nextPrefix.length()), mappings)) {
            return true;
          } else {
            mappings.remove(next);
          }
        }
      }
      return false;
    }
  }

  /**
   * Pond Sizes: Provided a matrix of land heights where 0 represents water and
   *   water connected adjacently or diagonally are considered a pond. Write a
   *   function to return all pond sizes in the matrix.
   *
   * Assumptions:
   *   valid input, square and proper values, at least 1x1
   *   can modify the input
   *
   * Time complexity: O(CR) where C is number of columns and R is number of rows.
   * Space complexity: O(1)
   *
   * Difference with book: I don't look at 8 spaces around the position in
   *   'findPondSize' because I'm traversing downward in the matrix.
   */
  public static Set<Integer> findPondSizes(final int[][] topography) {
    final Set<Integer> pondSizes = Sets.newHashSet();
    for (int col = 0; col < topography.length; col++) {
      for (int row = 0; row < topography[0].length; row++) {
        if (topography[col][row] == 0) { // not necessary, probably slightly less comparisons though
          int size = findPondSize(topography, col, row, topography.length, topography[0].length);
          pondSizes.add(size);
        }
      }
    }
    return pondSizes;
  }

  private static int findPondSize(
      final int[][] topography,
      final int col, final int row,
      final int colSize, final int rowSize
  ) {
    if (col >= colSize || row >= rowSize || row < 0 || topography[col][row] != 0) {
      return 0;
    } else {
      topography[col][row] = -1;
      return 1 +
        // below
        findPondSize(topography, col+1, row, colSize, rowSize) +
        // right
        findPondSize(topography, col, row+1, colSize, rowSize) +
        // left
        findPondSize(topography, col, row-1, colSize, rowSize) +
        // diagonal right
        findPondSize(topography, col+1, row+1, colSize, rowSize) +
        // diagonal left
        findPondSize(topography, col+1, row-1, colSize, rowSize);
    }
  }

  /**
   * Sum Swap: Given two arrays of integers, find an integer in both arrays such that
   *   if they were swapped the arrays would have the same sum.
   *
   * Assumptions:
   *   if no such integers, return null
   *
   * Time complexity: O(a + b)
   * Space complexity: O(b) could be improved to choose the shorter at the cost of a little complexity
   *
   * Note: If I sort, could use O(1) space, but at cost of O(a log a + b log b) for the sort.
   */
  public static int[] sumSwap(final int[] a, final int[] b) {
    final int sumA = sum(a);
    final int sumB = sum(b);
    if ((sumA + sumB) % 2 != 0) {
      // odd total sum, can't make these equal
      return null;
    }
    final int targetSum = (sumA + sumB)/2;
    return findDifference(targetSum - sumA, a, b);
  }

  private static int[] findDifference(final int difference, final int[] a, final int[] b) {
    final Set<Integer> bSet = toSet(b);
    for (int j : a) {
      if (bSet.contains(j + difference)) {
        return new int[]{j, j + difference};
      }
    }
    return null;
  }

  private static Set<Integer> toSet(int[] a) {
    final Set<Integer> set = Sets.newHashSet();
    for (int j : a) {
      set.add(j);
    }
    return set;
  }

  private static int sum(final int[] a) {
    int sum = 0;
    for (int j : a) {
      sum += j;
    }
    return sum;
  }

  public static class AntGrid {
    enum Direction {
      Right, Down, Left, Up
    }

    // if in the set, it's black
    private final Set<Tuple2<Integer, Integer>> grid;
    private int antCol = 0;
    private int antRow = 0;
    private Direction antDirection = Direction.Right;

    public AntGrid() {
      grid = Sets.newHashSet();
    }

    public void moveAnt() {
      final boolean isBlack = flipCurrentColor();
      if (!isBlack) {
        moveWhite();
      } else {
        moveBlack();
      }
    }

    // returns old value and flips the current color
    private boolean flipCurrentColor() {
      final Tuple2<Integer, Integer> antPosition = new Tuple2<>(antCol, antRow);
      final boolean oldValue = grid.remove(antPosition);
      if (!oldValue) {
        grid.add(antPosition);
      }
      return oldValue;
    }

    private void moveWhite() {
      turnClockwise();
      moveForward();
    }

    private void moveBlack() {
      turnCounterClockwise();
      moveForward();
    }

    private void moveForward() {
      switch (antDirection) {
        case Right: antRow+=1;
          break;
        case Down: antCol+=1;
          break;
        case Left: antRow-=1;
          break;
        case Up: antCol-=1;
          break;
      }
    }

    private void turnClockwise() {
      antDirection = Direction.values()[(antDirection.ordinal() + 1) % Direction.values().length];
    }
    private void turnCounterClockwise() {
      antDirection = Direction.values()[(antDirection.ordinal() + Direction.values().length - 1) % Direction.values().length];
    }

    public AntGridResult getResult() {
      final GridDimensions dimensions = getGridDimensions();
      final Tuple2<Integer, Integer> ant = new Tuple2<>(antCol - dimensions.minY, antRow - dimensions.minX);
      boolean[][] isBlack = new boolean[dimensions.maxY - dimensions.minY + 1][dimensions.maxX - dimensions.minX + 1];
      for (Tuple2<Integer, Integer> black: grid) {
        isBlack[black._1 - dimensions.minY][black._2 - dimensions.minX] = true;
      }
      return new AntGridResult(ant, isBlack, antDirection);
    }

    private GridDimensions getGridDimensions() {
      int minX = antRow, maxX = antRow;
      int minY = antCol, maxY = antCol;
 
      for (Tuple2<Integer, Integer> black: grid) {
        int y = black._1;
        int x = black._2;
        minY = Math.min(minY, y);
        minX = Math.min(minX, x);
        maxY = Math.max(maxY, y);
        maxX = Math.max(maxX, x);
      }
      return new GridDimensions(minX, maxX, minY, maxY);
    }

    private static class GridDimensions {
      public final int minX, maxX, minY, maxY;

      public GridDimensions(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
      }
    }

    public static class AntGridResult {
      public final Tuple2<Integer, Integer> ant;
      public final boolean[][] isBlack;
      public final Direction direction;

      public AntGridResult(Tuple2<Integer, Integer> ant, boolean[][] isBlack, Direction direction) {
        this.ant = ant;
        this.isBlack = isBlack;
        this.direction = direction;
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ant == null) ? 0 : ant.hashCode());
        result = prime * result
            + ((direction == null) ? 0 : direction.hashCode());
        result = prime * result + Arrays.deepHashCode(isBlack);
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
        AntGridResult other = (AntGridResult) obj;
        if (ant == null) {
          if (other.ant != null)
            return false;
        } else if (!ant.equals(other.ant))
          return false;
        if (direction != other.direction)
          return false;
        return Arrays.deepEquals(isBlack, other.isBlack);
      }      
    }
  }

  /**
   * Langton's Ant: Given an infinite black and white grid and an and facing to the
   *   right, simulate the effect of the ant moving around follow it's rules:
   *     1. At white, flip the color to black, turn 90 CW and move forward 1.
   *     2. At black, flip the color to white, turn 90 CCW and move forward 1.
   *   Simulate the first k moves of the ant and return the grid.
   *
   * Assumptions:
   *
   * Time complexity: O(1) for each move
   * Space complexity: O() impact minimized by using hashset
   */
  public static AntGridResult antWalk(final int k) {
    final AntGrid grid = new AntGrid();
    for (int i = 0; i < k; i++) {
      grid.moveAnt();
    }
    return grid.getResult();
  }

  /**
   * Rand7 from Rand5: Given a 'rand5' function that give random numbers from 0 until
   *  5, create a function that creates a random number 0 until 7 using 'rand5' with
   *  an equal probability across all possible values.
   *
   * Assumptions:
   *
   * Time complexity: O() - non-deterministic
   * Space complexity: O(1)
   */
  public static int rand7() {
    int num = rand5() + 5*rand5();
    if (num >= (25/7) * 7) {
      // non-deterministic finish
      return rand7();
    }

    return num % 7;
  }

  private static final Random random = new Random();

  private static int rand5() {
    return random.nextInt(5);
  }

  /**
   * Tuple2s with Sum: Given an integer array and a sum, find all pairs with the sum.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   *
   * Note: This is all about trade offs with time complexity and space.
   */
  public static Map<Integer, Integer> findPairsWithSum(final int[] a, final int sum) {
    final Set<Integer> seen = Sets.newHashSet();
    final Map<Integer, Integer> pairs = Maps.newHashMap();
    for (int next : a) {
      if (!pairs.containsKey(next) || !pairs.containsKey(sum - next)) {
        if (seen.contains(sum - next)) {
          pairs.put(Math.min(next, sum - next), Math.max(next, sum - next));
          seen.remove(sum - next);
        } else {
          seen.add(next);
        }
      }
    }
    return pairs;
  }

  /**
   * LRU Cache: Make a LRU cache with a max size.
   *
   * Assumptions:
   *
   * Time complexity: O(n)
   * Space complexity: O(n)
   *
   * Note: This is a bit sloppy but trying to do this quickly as if in an interview.
   */
  public static class LRUCache<K, V> {
    final int maxSize;
    final Map<K, Node<K,V>> cache = Maps.newHashMap();
    Node<K, V> queueHead = null;
    Node<K, V> queueTail = null;
    int queueSize = 0;

    private class Node<A, B> {
      final A key;
      B value;
      Node<A, B> next;
      Node<A, B> previous;

      public Node(final A key) {
        this.key = key;
      }

      public B updateValue(B value) {
        final B oldValue = this.value;
        this.value = value;
        return oldValue;
      }
    }

    public LRUCache(int maxSize) {
      super();
      this.maxSize = maxSize;
    }

    public V get(K key) {
      return moveToRearOfQueue(key).value;
    }

    public V put(K key, V value) {
      Node<K, V> node;
      if (containsKey(key)) {
        node = moveToRearOfQueue(key);
      } else {
        node = new Node<>(key);
        addToRear(node);
        cache.put(key, node);
      }
      return node.updateValue(value);
    }

    private Node<K, V> moveToRearOfQueue(K key) {
      final Node<K, V> node = cache.get(key);
      if (queueTail != node) {
        if (node != queueHead) {
          node.previous.next = node.next;
        } else {
          queueHead = node.next;
        }

        node.next.previous = node.previous;
        node.next = null;
        node.previous = null;
        queueSize--;
        addToRear(node);
      }
      return node;
    }

    private void addToRear(LRUCache<K, V>.Node<K, V> node) {
      evict();
      if (queueTail == null) {
        queueHead = node;
      } else {
        queueTail.next = node;
        node.previous = queueTail;
      }
      queueTail = node;
      queueSize++;
    }

    private void evict() {
      if (queueSize + 1 > maxSize) {
        removeHead();
      }
    }

    private void removeHead() {
      final Node<K, V> toRemove = queueHead;
      queueHead = queueHead.next;
      cache.remove(toRemove.key);
      queueSize--;      
    }

    public boolean containsKey(K key) {
      return cache.containsKey(key);
    }
  }

  /**
   * Calculator: Given an arithmetic expression as a String compute it's value. There
   *   are no parenthesis and valid operators are -,+,* and /. There are only positive
   *   integers as values.
   *
   * Assumptions:
   *
   * Time complexity: O(c) where c is the number of characters
   * Space complexity: O(o) where o is the number of addition and subtraction operators
   *
   * Note: This is pretty highly tuned. I did a lot of work after solving it to
   *   reduce the lines of code greatly. It only iterates through the string up to
   *   three times, first to see where to split, second to subString, third to convert
   *   strings to integers.
   */
  public static double calculate(final String expression) {
    return calculate(expression, 0);
  }

  // start-inclusive, end-exclusive
  private static double calculate(final String expression, final int start) {
    double value = 0;
    int valueStart = start;
    char operation = ' '; // placeholder operation
    for (int i = start; i < expression.length(); i++) {
      final char next = expression.charAt(i);
      double nextValue;
      if (isOperator(next)) {
        final char lastOperation = operation;
        operation = next;

        nextValue = Integer.parseInt(expression.substring(valueStart, i));
        value = calculate(lastOperation, value, nextValue);
        valueStart = i + 1;

        if (next == '+') {
          return value + calculate(expression, i + 1);        
        } else if (next == '-') {
          return value - calculate(expression, i + 1);
        }
      }
    }
    double secondValue = Integer.parseInt(expression.substring(valueStart));
    return calculate(operation, value, secondValue);
  }

  private static boolean isOperator(char c) {
    return (c == '*') || (c == '/') || (c == '+') || (c == '-');
  }

  private static double calculate(final char operation, final double a, final double b) {
    if (operation == '*') {
      return a * b;
    } else if (operation == '/') {
      return a / b;
    } else if (operation == '+') {
      return a + b;
    } else if (operation == '-') {
      return a - b;
    } else {
      return b;
    }
  }

  /**
   * Calculator: Given an arithmetic expression as a String compute it's value. There
   *   are no parenthesis and valid operators are -,+,* and /. There are only positive
   *   integers as values.
   *
   * Assumptions:
   *
   * Time complexity: O(c) where c is the number of characters
   * Space complexity: O(c)
   *
   * Note: I think I like this way better, it's a lot more understandable. It uses some
   *   extra space, but it is way more readable.
   */
  public static double calculate2(final String expression) {
    Tuple2<List<String>, List<String>> split = splitExpression(expression);
    final List<String> numbers = split._1;
    final List<String> operators = split._2;

    if (numbers.size() != operators.size()+1) {
      throw new IllegalStateException(String.format("Invalid expression. numbers = %s, ops = %s", numbers.size(), operators.size()));
    }

    return calculate2(numbers, operators, 0);
  }

  private static Tuple2<List<String>, List<String>> splitExpression(final String expression) {
    List<String> operatorList = Lists.newArrayList();
    List<String> operandList = Lists.newArrayList();
    StringTokenizer st = new StringTokenizer(expression, "+-*/", true);
    while (st.hasMoreTokens()) {
       String token = st.nextToken();

       if ("+-/*".contains(token)) {
          operatorList.add(token);
       } else {
          operandList.add(token);
       }
    }
    return new Tuple2<>(operandList, operatorList);
  }

  private static double calculate2(final List<String> numbers, final List<String> operators, final int start) {
    double value = Integer.parseInt(numbers.get(start));
    for (int i = start; i < operators.size(); i++) {
      String nextOperator = operators.get(i);
      if ("*".equals(nextOperator)) {
        double nextValue = Integer.parseInt(numbers.get(i + 1));
        value *= nextValue;
      } else if ("/".equals(nextOperator)) {
        double nextValue = Integer.parseInt(numbers.get(i + 1));
        value /= nextValue;
      } else if ("+".equals(nextOperator)) {
        return value + calculate2(numbers, operators, i + 1);
      } else if ("-".equals(nextOperator)) {
        return value - calculate2(numbers, operators, i + 1);
      } else {
        throw new IllegalArgumentException("Unknown operator");
      }
    }
    return value;
  }
}
