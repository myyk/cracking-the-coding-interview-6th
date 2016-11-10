package com.github.myyk.cracking;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javafx.util.Pair;

import com.github.myyk.cracking.Chapter16Solutions.MutableInteger;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
   * Time complexity: O(n)
   * Space complexity: O(n)
   *
   * Difference with book: In many cases my algorithm has lower constants, but overall is very similar.
   *   Mine will perform better if the the average chance of any given letter being a letter or a number
   *   is about even as there will be full traverses through the dataset.
   */
  public static char[] findLongestSubArrayWithEqualLettersAndNumbers(final char[] chars) {
    final Map<Integer, Pair<Integer, Integer>> excessLettersToIndices = computeExcessLetters(chars);
    int longestSize = 0;
    Pair<Integer, Integer> longestIndices = null;
    for (final Map.Entry<Integer, Pair<Integer, Integer>> next : excessLettersToIndices.entrySet()) {
      final Pair<Integer, Integer> indices = next.getValue();
      if (indices.getValue() - indices.getKey() > longestSize) {
        longestSize = indices.getValue() - indices.getKey();
        longestIndices = indices;
      }
    }
 
    if (longestSize == 0) {
      return new char[0];
    } else {
      return Arrays.copyOfRange(chars, longestIndices.getKey(), longestIndices.getValue());
    }
  }

  /*
   * From each character keep track of the number of unpaired letters seen before it. If that number has
   * been seen before, then there is a equal subarray from when we first had that number of unpaired
   * letters until this index. As we find new subarrays that overlap, we can always take the new end index
   * as the new array should include all of the old array to be the longest.
   */
  private static Map<Integer, Pair<Integer, Integer>> computeExcessLetters(char[] chars) {
    final Map<Integer, Pair<Integer, Integer>> excessLettersToIndices = Maps.newHashMap();
    int excessLetters = 0;
    for (int i = 0; i <= chars.length; i++) {
      final Pair<Integer, Integer> indices = excessLettersToIndices.getOrDefault(excessLetters, new Pair<Integer, Integer>(i, i));
      excessLettersToIndices.put(excessLetters, new Pair<Integer, Integer>(indices.getKey(), i));
      if (i < chars.length && Character.isLetter(chars[i])) {
        excessLetters += 1;
      } else {
        excessLetters -= 1;
      }
    }
    return excessLettersToIndices;
  }

  /**
   * Counts of 2: Find the number of 2s in numbers 0 to n inclusive.
   *
   * Assumptions:
   *
   * Time complexity: O(log10(n))
   * Space complexity: O(1)
   */
  public static int countsOfTwo(final int n) {
    if (n < 0) {
      return 0;
    }

    int remaining = n;
    int magnitude = 1;
    int twos = 0;
    while (remaining > 0) {
      final int digit = remaining % 10;
      if (digit >= 3) {
        twos += magnitude;
      } else if (digit == 2) {
        twos += (n % (magnitude * 10)) - (2 * magnitude) + 1;
      }

      twos += remaining/10 * magnitude;
      remaining /= 10;
      magnitude *= 10;
    }
    return twos;
  }

  /**
   * Baby Names: Given baby names and their frequency and a list of equivalent name pairs. Reduce the data
   *   to a synonym to the total of all equivalent synonym to that name.
   *
   * Assumptions:
   *  synonym relationships are transitive
   *  all data is valid
   *  remove names with no frequency
   *
   * Time complexity: O(names + synonyms)
   * Space complexity: O(names)
   */
  public static Map<String, Integer> babyNameFrequencyReduction(final Map<String, Integer> frequencies, final List<Pair<String, String>> synonmys) {
    final Map<String, Set<String>> nameSets = Maps.newHashMap();
    for (final Pair<String, String> syn: synonmys) {
      mergeSets(nameSets, syn.getKey(), syn.getValue());
    }
    // add missing nameSets that have no syn
    for (final Map.Entry<String, Integer> nameFreq: frequencies.entrySet()) {
      if (!nameSets.containsKey(nameFreq.getKey())) {
        nameSets.put(nameFreq.getKey(), Sets.newHashSet(nameFreq.getKey()));
      }
    }

    return babyNameFrequencyReduction(nameSets, frequencies);
  }

  private static Map<String, Integer> babyNameFrequencyReduction(final Map<String, Set<String>> nameSets, final Map<String, Integer> frequencies) {
    final Map<String, Integer> result = Maps.newHashMap();
    for (final Set<String> nameSet : Sets.newHashSet(nameSets.values())) {
      int total = 0;
      String minName = null;
      for (final String next : nameSet) {
        if (minName == null || next.compareTo(minName) < 0) {
          minName = next;
        }
        total += frequencies.getOrDefault(next, 0);
      }
      if (total > 0) {
        result.put(minName, total);
      }
    }
    return result;
  }

  private static void mergeSets(final Map<String, Set<String>> nameSets, final String a, final String b) {
    if (nameSets.containsKey(a) != nameSets.containsKey(b)) {
      final Set<String> nameSet = nameSets.getOrDefault(a, nameSets.get(b));
      nameSet.add(a);
      nameSet.add(b);
      nameSets.put(a, nameSet);
      nameSets.put(b, nameSet);
    } else if (!nameSets.containsKey(a)) {
      final Set<String> nameSet = Sets.newHashSet(a, b);
      nameSets.put(a, nameSet);
      nameSets.put(b, nameSet);
    } else {
      // merge 2 sets
      final Set<String> nameSetA = nameSets.get(a);
      final Set<String> nameSetB = nameSets.get(b);
      for (String nextB : nameSetB) {
        nameSets.put(nextB, nameSetA);
        nameSetA.add(nextB);
      }
    }
  }

  public static class Person {
    final int height;
    final int weight;

    public static final Person MAX_VALUE = new Person(Integer.MAX_VALUE, Integer.MAX_VALUE);

    public Person(final int height, final int weight) {
      this.height = height;
      this.weight = weight;
    }

    public int getHeight() {
      return height;
    }

    public int getWeight() {
      return weight;
    }

    public boolean smallerThan(final Person other) {
      return height < other.height && weight < other.weight;
    }
  }

  /**
   * Circus Tower: Given a list of people who have a height and a weight and can stack only where the person on top is
   *   both shorter and lighter. How many people high can the person tower be?
   *
   * Assumptions:
   *  ties can't stack
   *
   * Time complexity: O(?)
   * Space complexity: O(?) at least p^2
   *
   * Note: This could have a better performance. Probably could sort on both dimensions first.
   */
  public static int circusTowerHeight(final Set<Person> people) {
    return circusTowerHeight(Person.MAX_VALUE, people, Maps.<Person, Integer>newHashMap());
  }

  private static int circusTowerHeight(final Person base, final Set<Person> people, final Map<Person, Integer> memo) {
    if (people.isEmpty()) {
      return 0;
    } else if (memo.containsKey(base)) {
      return memo.get(base);
    } else {
      final Set<Person> smaller = Sets.newHashSet();
      for (final Person person : people) {
        if (person.smallerThan(base)) {
          smaller.add(person);
        }
      }
      int maxHeight = 0;
      for(Person next : smaller) {
        final Set<Person> smallerCopy = Sets.newHashSet(smaller);
        smallerCopy.remove(next);
        maxHeight = Math.max(maxHeight, circusTowerHeight(next, smallerCopy, memo));
      }
      memo.put(base, maxHeight + 1);
      return maxHeight + 1;
    }
  }
}
