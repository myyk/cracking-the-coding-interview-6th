package com.github.myyk.cracking;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import scala.Tuple2;

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
    final Map<Integer, Tuple2<Integer, Integer>> excessLettersToIndices = computeExcessLetters(chars);
    int longestSize = 0;
    Tuple2<Integer, Integer> longestIndices = null;
    for (final Map.Entry<Integer, Tuple2<Integer, Integer>> next : excessLettersToIndices.entrySet()) {
      final Tuple2<Integer, Integer> indices = next.getValue();
      if (indices._2 - indices._1 > longestSize) {
        longestSize = indices._2 - indices._1;
        longestIndices = indices;
      }
    }
 
    if (longestSize == 0) {
      return new char[0];
    } else {
      return Arrays.copyOfRange(chars, longestIndices._1, longestIndices._2);
    }
  }

  /*
   * From each character keep track of the number of unpaired letters seen before it. If that number has
   * been seen before, then there is a equal subarray from when we first had that number of unpaired
   * letters until this index. As we find new subarrays that overlap, we can always take the new end index
   * as the new array should include all of the old array to be the longest.
   */
  private static Map<Integer, Tuple2<Integer, Integer>> computeExcessLetters(char[] chars) {
    final Map<Integer, Tuple2<Integer, Integer>> excessLettersToIndices = Maps.newHashMap();
    int excessLetters = 0;
    for (int i = 0; i <= chars.length; i++) {
      final Tuple2<Integer, Integer> indices = excessLettersToIndices.getOrDefault(excessLetters, new Tuple2<>(i, i));
      excessLettersToIndices.put(excessLetters, new Tuple2<Integer, Integer>(indices._1, i));
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
  public static Map<String, Integer> babyNameFrequencyReduction(final Map<String, Integer> frequencies, final List<Tuple2<String, String>> synonmys) {
    final Map<String, Set<String>> nameSets = Maps.newHashMap();
    for (final Tuple2<String, String> syn: synonmys) {
      mergeSets(nameSets, syn._1, syn._2);
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

  private static class NoDuplicatesPriorityQueue<E> extends PriorityQueue<E> 
  {
      @Override
      public boolean offer(E e) 
      {
          boolean isAdded = false;
          if(!super.contains(e))
          {
              isAdded = super.offer(e);
          }
          return isAdded;
      }
  }

  /**
   * Kth Multiple: Find the Kth number such that it's only prime factors are 3, 5, and 7.
   *
   * Assumptions:
   *  Just doing this one time, otherwise make this like a Stream, I guess an Iterator in Java.
   *
   * Time complexity: O(k log k)
   * Space complexity: O(?) // something less than k.
   *
   * Note: This solution is easy to understand but a little slower than the optimal solution.
   */
  public static int kthNumber(final int k) {
    final PriorityQueue<Integer> numbers = new NoDuplicatesPriorityQueue<Integer>();
    numbers.offer(1);

    int i = 0;
    int number = 1;
    while(i < k) {
      number = numbers.poll();
      numbers.offer(number * 3);
      numbers.offer(number * 5);
      numbers.offer(number * 7);

      i++;
    }
    return number;
  }

  /**
   * Kth Multiple: Find the Kth number such that it's only prime factors are 3, 5, and 7.
   *
   * Assumptions:
   *  Just doing this one time, otherwise make this like a Stream, I guess an Iterator in Java.
   *
   * Time complexity: O(k)
   * Space complexity: O(?) // something less than k.
   */
  public static int kthNumber2(final int k) {
    final LinkedList<Integer> q3 = Lists.newLinkedList();
    final LinkedList<Integer> q5 = Lists.newLinkedList();
    final LinkedList<Integer> q7 = Lists.newLinkedList();
    q3.offer(3);
    q5.offer(5);
    q7.offer(7);

    int i = 1;
    int number = 1;
    while(i < k) {
      number = findMin(q3, q5, q7);
      updateQueues(number, q3, q5, q7);

      i++;
    }
    return number;
  }

  private static int findMin(final LinkedList<Integer> q3, final LinkedList<Integer> q5, final LinkedList<Integer> q7) {
    return Math.min(Math.min(q3.peek(), q5.peek()), q7.peek());
  }

  private static void updateQueues(final int min, final LinkedList<Integer> q3, final LinkedList<Integer> q5, final LinkedList<Integer> q7) {
    if (q3.peek().equals(min)) {
      q3.pop();
      q3.offer(3*min);
      q5.offer(5*min);
      q7.offer(7*min);
    } else if (q5.peek().equals(min)) {
      q5.pop();
      q5.offer(5*min);
      q7.offer(7*min);
    } else {
      q7.pop();
      q7.offer(7*min);
    }
  }

  /**
   * Majority Element: Find an value such that the value represents a majority of the elements in the positive integer
   *   array. Return -1 if there is no such element.
   *
   * Assumptions:
   *   majority means > array.length / 2
   *
   * Time complexity: O(n)
   * Space complexity: O(1)
   */
  public static int findMajority(int[] array) {
    final int maybeMajority = findPossibleMajority(array);
    if (maybeMajority > 0 && isValidMajority(maybeMajority, array)) {
      return maybeMajority;
    } else {
      return -1;
    }
  }

  private static int findPossibleMajority(final int[] a) {
    int maybeMajority = -1;
    int majorityCount = 0;
    for (int i = 0; i < a.length; i++) {
      if (majorityCount == 0) {
        maybeMajority = a[i];
      }
      
      if (maybeMajority == a[i]) {
        majorityCount++;
      } else {
        majorityCount--;
      }
    }
    return maybeMajority;
  }

  private static boolean isValidMajority(final int maybeMajority, final int[] array) {
    int found = 0;
    for (int next : array) {
      if (next == maybeMajority) {
        found++;
        if (found > array.length / 2) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Word Distance: Given a large text file of words and two words. Find the shortest distance between the two words.
   *
   * Assumptions:
   *   the word exists
   *   pre-process dictionary
   *
   * Pre-processing:
   * Time complexity: O(w) where is the number of non-distinct words
   * Space complexity: O(w)
   *
   * Time complexity: O(a + b) where a and b are the number of occurrences of the two words
   * Space complexity: O(1)
   */
  public static int wordDistance(final String a, final String b, final Map<String, List<Integer>> wordPositions) {
    final Iterator<Integer> occA = wordPositions.get(a).iterator();
    final Iterator<Integer> occB = wordPositions.get(b).iterator();
    int minDistance = Integer.MAX_VALUE;
    int nextA = occA.next();
    int nextB = occB.next();
    while (occA.hasNext() && occB.hasNext()) {
      if (nextA > nextB) {
        minDistance = Math.min(minDistance, nextA - nextB);
        nextB = occB.next();
      } else {
        minDistance = Math.min(minDistance, nextB - nextA);
        nextA = occA.next();
      }
    }
    return minDistance;
  }

  public static Map<String, List<Integer>> wordPositions(final String[] words) {
    final Map<String, List<Integer>> result = Maps.newHashMap();
    for (int i = 0; i < words.length; i++) {
      result.getOrDefault(words[i], Lists.<Integer>newLinkedList()).add(i);
    }
    return result;
  }
}
