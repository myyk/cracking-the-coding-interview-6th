package com.github.myyk.cracking.chapter7;

import java.util.ArrayList;
import java.util.Iterator;

public class CircularArray<T> implements Iterable<T> {
  public final ArrayList<T> list = new ArrayList<T>();
  private int leftRotations = 0;

  public CircularArray() {
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      //TODO: add concurrent modification protection
      int i = getFirstIndex();

      @Override
      public boolean hasNext() {
        return i != getLastIndex();
      }

      @Override
      public T next() {
        T result = list.get(i);
        i = (i + 1) % list.size();
        return result;
      }
    };
  }

  private int getFirstIndex() {
    if (list.isEmpty()) {
      return 0;
    }
    return leftRotations % list.size();
  }

  private int getLastIndex() {
    if (list.isEmpty()) {
      return 0;
    }
    return (getFirstIndex() + list.size() - 1) % list.size(); 
  }

  public T add(T e) {
    if (list.isEmpty() || leftRotations % list.size() == 0) {
      list.add(e);
    } else {
      list.add(getFirstIndex(), e);
      leftRotations += 2;
    }
    return e;
  }

  public void rotateRight(int n) {
    leftRotations += list.size() - n;
  }

  public void rotateLeft(int n) {
    leftRotations += n;
  }
}
