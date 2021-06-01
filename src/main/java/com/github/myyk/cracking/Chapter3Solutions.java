package com.github.myyk.cracking;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

import com.google.common.collect.Lists;

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

    final private Stack<T> minStack = new Stack<>();

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
    private static final long serialVersionUID = -2895833133191086808L;

    private final int threshold;
    private final Stack<Stack<T>> stacks;

    public SetOfStacks(final int threshold) {
      super();
      if (threshold < 0) {
        throw new IllegalArgumentException();
      }
      this.threshold = threshold;
      this.stacks = new Stack<>();
    }

    private void checkConsistency() {
      for (Stack<T> stack : stacks) {
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
      Stack<T> newStack = new Stack<>();
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
   *   all the irrelevant to the parts of the interface will throw exceptions
   *
   * Design:
   */
  public static class QueueFromStacks<T> implements Queue<T> {
    final Stack<T> front = new Stack<>();
    final Stack<T> back = new Stack<>();

    /**
     * Moves front to back.
     */
    private void popAllFront() {
      if (!back.isEmpty()) {
        throw new IllegalStateException("Can't pop all from front if there's still elements in the back.");
      }
      while (!front.isEmpty()) {
        back.push(front.pop());
      }
    }

    @Override
    public int size() {
      return front.size() + back.size();
    }

    @Override
    public boolean isEmpty() {
      return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
      return front.contains(o) || back.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
      throw new UnsupportedOperationException();
    }

    @Override
    public <E> E[] toArray(E[] a) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
      if (back.remove(o)) {
        return true;
      } else {
        ListIterator<T> it = front.listIterator(front.size());
        while (it.hasPrevious()) {
          if (it.previous().equals(o)) {
            it.remove();
            return true;
          }
        }
      }
      return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
      for (T next: c) {
        front.push(next);
      }
      return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
      Iterator<?> it = c.iterator();
      boolean result = true;
      while (it.hasNext()) {
        result &= remove(it.next());
      }
      return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
      front.clear();
      back.clear();
    }

    @Override
    public boolean add(T e) {
      front.push(e);
      return true;
    }

    @Override
    public boolean offer(T e) {
      front.push(e);
      return true;
    }

    @Override
    public T remove() {
      if (isEmpty()) {
        throw new NoSuchElementException("Cannot remove when queue is empty.");
      }
      return poll();
    }

    @Override
    public T poll() {
      if (isEmpty()) {
        return null;
      } else if (back.isEmpty()) {
        popAllFront();
      }
     
      return back.pop();
    }

    @Override
    public T element() {
      if (isEmpty()) {
        throw new NoSuchElementException("Cannot see element when queue is empty.");
      }
      return peek();
    }

    @Override
    public T peek() {
      if (isEmpty()) {
        return null;
      } else if (back.isEmpty()) {
        popAllFront();
      }

      return back.peek();
    }
  }

  /**
   * Sort Stack: Sort a stack only using another stack.
   *
   * Assumptions:
   *   sort ascending
   *   in-place sorting
   *
   * Time complexity: O(n^2)
   * Space complexity: O(n)
   */
  public static <T extends Comparable<? super T>> void sortStack(Stack<T> stack) {
    Stack<T> temp = new Stack<>();
    while (!stack.isEmpty()) {
      T next = stack.pop();
      while (!temp.isEmpty() && next.compareTo(temp.peek()) >= 0) {
        stack.push(temp.pop());
      }
      temp.push(next);
    }

    while (!temp.isEmpty()) {
      stack.push(temp.pop());
    }
  }

  /**
   * Animal Shelter: Animals are given out in a LIFO basis. Adopters can choose between
   *   cat or dog.
   *
   * Assumptions:
   *   can use java.util.LinkedList
   *
   * Time complexity and Space complexity:
   *   Enqueues are O(1) because it's just the end linked list.
   *   Dequeues are O(1) since we just check which of the two queues to look at.
   *
   * If I were to expand on this, I'm probably have a pet type to queue map. So I could look
   * up the different animals more easily. Maybe also the dequeue method could take a collection
   * of possible animals that the adopter would like.
   */
  public static class AnimalShelter {
    public static abstract class Pet {
      private int id;
      public Pet(int id) { this.id = id; }

      public int getId() { return id; }
      public void setId(int id) { this.id = id; }
    }
    public static class Dog extends Pet {
      public Dog(int id) { super(id); }
    }
    public static class Cat extends Pet {
      public Cat(int id) { super(id); }
    }

    public int nextId = 0; // this give ordering of when pets were added
    public LinkedList<Dog> dogs = Lists.newLinkedList();
    public LinkedList<Cat> cats = Lists.newLinkedList();

    public Pet enqueue(Pet pet) {
      pet.setId(nextId++);
      if (pet instanceof Dog) {
        dogs.addLast((Dog) pet);
      } else if (pet instanceof Cat) {
        cats.addLast((Cat) pet);
      }
      return pet;
    }

    public Cat dequeueCat() {
      if (cats.isEmpty()) {
        throw new NoSuchElementException("There are no more cats to adopt.");
      }
      return cats.removeFirst();
    }

    public Dog dequeueDog() {
      if (dogs.isEmpty()) {
        throw new NoSuchElementException("There are no more dogs to adopt.");
      }
      return dogs.removeFirst();
    }

    public Pet dequeueAny() {
      if (dogs.isEmpty() && cats.isEmpty()) {
        throw new NoSuchElementException("There are no more pets to adopt.");
      } else if (dogs.isEmpty()) {
        return dequeueCat();
      } else if (cats.isEmpty()) {
        return dequeueDog();
      } else {
        if (cats.peek().getId() < dogs.peek().getId()) {
          return dequeueCat();
        } else {
          return dequeueDog();
        }
      }
    }
  }
}
