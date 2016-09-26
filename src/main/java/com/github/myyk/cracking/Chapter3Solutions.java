package com.github.myyk.cracking;

import java.util.EmptyStackException;
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
      validateStackNumber(stackNum);
      int start = stackStartIndexes[stackNum];
      if (start >= stackCapacity/numberOfStacks*(stackNum+1)) {
        throw new FullStackException();
      }
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
   *
   * Design:
   *   Use a stack for storing the mins, pop min when popping if it equals the top of the min stack.
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

  /**
   * Stacks of Plates: A stack that when it gets larger than a threshold it creates another stack.
   *   There's a new method popAt(int index) which pops from the specified sub-stack.
   *
   * Assumptions:
   *   I should remove empty sub-stacks
   *   sub-stacks are indexed like stacks, next to pop is at size() - 1, first pushed at 0.
   *   it's okay to have sub-indexes in the middle at less than full capacity
   *
   * Design:
   *   Extending Stack<T> doesn't really make sense, but there's no Java interface for Stack, so...
   */
  public static class SetOfStacks<T> extends Stack<T> {
    private static final long serialVersionUID = -9099626550812598651L;

    private final int threshold;
    private final Stack<Stack<T>> stacks;

    public SetOfStacks(final int threshold) {
      super();
      if (threshold < 0) {
        throw new IllegalArgumentException();
      }
      this.threshold = threshold;
      this.stacks = new Stack<Stack<T>>();
    }

    private void checkConsistency() {
      for (int i = 0; i < stacks.size(); i++) {
        Stack<T> stack = stacks.get(i);
        if (stack.isEmpty()) {
          throw new IllegalStateException("sub-stacks should never be empty");
        }
      }
    }

    @Override
    public T push(T item) {
      T result;
      if (stacks.isEmpty() || stacks.peek().size() >= threshold) {
        result = pushOnNewStack(item);
      } else {
        result = pushOnCurrentStack(item);
      }
      checkConsistency();
      return result;
    }

    private T pushOnCurrentStack(T item) {
      return stacks.peek().push(item);
    }

    private T pushOnNewStack(T item) {
      Stack<T> newStack = new Stack<T>();
      newStack.push(item);
      stacks.push(newStack);
      return item;
    }

    @Override
    public T pop() {
      //delegate exception handling to Stack<T> implementation.
      T result = stacks.peek().pop();
      if (stacks.peek().isEmpty()) {
        removeStack(stacks.size() - 1);
      }
      checkConsistency();
      return result;
    }

    private Stack<T> removeStack(int i) {
      return stacks.remove(i);
    }

    public T popAt(int index) {
      Stack<T> stack = stacks.get(index);
      T result = stack.pop();
      if (stack.empty()) {
        removeStack(index);
      }
      return result;
    }

    @Override
    public boolean isEmpty() {
      return stacks.isEmpty();
    }
  }

  /**
   * Queue via Stacks: Implement a queue using two stacks.
   *
   * Assumptions:
   *
   * Design:
   */
  public static class QueueFromStacks {
    
  }
}
