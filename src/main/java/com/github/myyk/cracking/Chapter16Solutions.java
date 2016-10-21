package com.github.myyk.cracking;

import java.util.Map;

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
}
