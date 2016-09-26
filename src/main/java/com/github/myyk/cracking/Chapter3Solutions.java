package com.github.myyk.cracking;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Stacks and Queues
 */
public class Chapter3Solutions {

  public static class FullStackException extends RuntimeException {
    private static final long serialVersionUID = -8002601578576477109L;
  }

  /**
   * Three In One: Implement 3 stacks with 1 array.
   *
   * Assumptions:
   *   fixed size array and divisions is ok to reduce complexity in an interview setting.
   *   stackNum is zero-indexed
   *
   * push:
   *   Time complexity: O(1)
   * pop:
   *   Time complexity: O(1)
   * peek:
   *   Time complexity: O(1)
   * isEmpty:
   *   Time complexity: O(1)
   */
  @SuppressWarnings("unchecked")
  public static class FixedMultiStack<T> {
    final int numberOfStacks = 3;
    final int stackCapacity;
    final int[] stackStartIndexes;
    final Object[] elements;

    public FixedMultiStack(final int stackCapacity) {
      if (stackCapacity < 0) {
        throw new IllegalArgumentException("stackCapacity should greater than 0");
      }
      this.stackCapacity = stackCapacity;
      this.stackStartIndexes = new int[numberOfStacks];
      this.elements = new Object[stackCapacity];
      for (int i = 0; i<numberOfStacks; i++) {
        stackStartIndexes[i] = stackCapacity/numberOfStacks*i;
      }
    }

    public boolean isEmpty(int stackNum) {
      validateStackNumber(stackNum);
      return (stackStartIndexes[stackNum] == stackCapacity/numberOfStacks*stackNum);
    }

    public T peek(int stackNum) {
      validateStackNumber(stackNum);
      if (isEmpty(stackNum)) {
        throw new EmptyStackException();
      }
      return ((T) elements[stackStartIndexes[stackNum]-1]);
    }

    public T pop(int stackNum) {
      T result = peek(stackNum);
      stackStartIndexes[stackNum]--;
      return result;
    }

    public void push(T elem, int stackNum) {
      System.out.println("pushing " + elem + " on stack " + stackNum);
      validateStackNumber(stackNum);
      int start = stackStartIndexes[stackNum];
      if (start >= stackCapacity/numberOfStacks*(stackNum+1)) {
        throw new FullStackException();
      }
      System.out.println("pushing " + elem + " on stack " + stackNum + " at element " + start);
      elements[start] = elem;
      stackStartIndexes[stackNum] = start+1;
    }

    private void validateStackNumber(int stackNum) {
      if (stackNum > numberOfStacks || stackNum < 0) {
        throw new IllegalArgumentException("stackNum should be less than " + numberOfStacks);
      }
    }
  }

  /**
   * Stack Min: Stack with a min() function that gets the minimum value in O(1) time with other
   *   stack functions still being O(1).
   *
   * Assumptions:
   *   stack only contains Integers
   *
   * Design:
   *   keep min, push (value, min) min is just like peekTuple._2
   */
  public static class StackWithMin<T extends Comparable<? super T>> extends Stack<T> {
    private static final long serialVersionUID = -1006279127078761653L;

    private Stack<T> minStack = new Stack<T>();

    public T min() {
      return minStack.peek();
    }

    @Override
    public T push(T i) {
      if (isEmpty() || i.compareTo(min()) <= 0) {
        minStack.push(i);
      }

      return super.push(i);
    }

    @Override
    public T pop() {
      T result = super.pop();
      if (result.compareTo(min()) <= 0) {
        minStack.pop();
      }
      return result;
    }
  }
}
