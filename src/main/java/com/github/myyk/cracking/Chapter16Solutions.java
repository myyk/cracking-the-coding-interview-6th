package com.github.myyk.cracking;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

/**
 * Moderate
 */
public class Chapter16Solutions {
  public static class MutableInteger {
    public int value;

    public MutableInteger(int value) {
      this.value = value;
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
    for (int i = 0; i < N; i++) {
      final TicTacToe next = board[i][0];
      if (next != null) {
        for (int j = 1; j < N; j++) {
          if (board[i][j] != next) {
            break;
          } else if (j == N-1) {
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
    private static Map<Integer, String> lookup = Maps.newHashMap();
    {{
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
      lookup.put(30, "Thrity");
      lookup.put(40, "Forty");
      lookup.put(50, "Fifty");
      lookup.put(60, "Sixty");
      lookup.put(70, "Seventy");
      lookup.put(80, "Eighty");
      lookup.put(90, "Ninty");
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
   * Time complexity: O()
   * Space complexity: O()
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
}
