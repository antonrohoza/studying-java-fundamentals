package com.antonr.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> implements List<T>, Iterable<T> {

  private static final double GROWTH_FACTOR = 1.5;
  private static final int DEFAULT_CAPACITY = 10;
  private Object[] elements;
  private int size;

  public ArrayList() {
    this(DEFAULT_CAPACITY);
  }

  public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
      elements = new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Initial capacity should be at least 1");
    }
  }

  @Override
  public void add(T value) {
    add(value, size);
  }

  @Override
  public void add(T value, int index) {
    ListUtils.checkIndex(index, size + 1);
    if (size == elements.length) {
      resize();
    }
    System.arraycopy(elements, index, elements, index + 1, size - index);
    elements[index] = value;
    size++;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T remove(int index) {
    ListUtils.checkIndex(index, size);
    T removedElement = (T) elements[index];
    if (index < size - 1) {
      System.arraycopy(elements, index + 1, elements, index, size - index - 1);
    }
    size--;
    elements[size] = null;
    return removedElement;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(int index) {
    ListUtils.checkIndex(index, size);
    return (T) elements[index];
  }

  @Override
  @SuppressWarnings("unchecked")
  public T set(Object value, int index) {
    ListUtils.checkIndex(index, size);
    T previousValue = (T) elements[index];
    elements[index] = value;
    return previousValue;
  }

  @Override
  public void clear() {
    elements = new Object[DEFAULT_CAPACITY];
    size = 0;
  }

  @Override
  public int size() {
    return size;
  }

  public int capacity(){
    return elements.length;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean contains(T value) {
    // Not indexOf(value) != -1; because this version is more effective.
    for (int i = 0; i < size / 2; i++) {
      if (Objects.equals(elements[i], value) || Objects.equals(elements[size - i - 1], value)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int indexOf(T value) {
    for (int i = 0; i < size; i++) {
      if (Objects.equals(elements[i], value)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(T value) {
    for (int i = size - 1; i >= 0; i--) {
      if (Objects.equals(elements[i], value)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public String toString() {
    StringJoiner sj = new StringJoiner(", ", "[", "]");
    for (int i = 0; i < size; i++) {
      sj.add(String.valueOf(elements[i]));
    }
    return sj.toString();
  }

  @Override
  @SuppressWarnings("unchecked")
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      int counter = -1;
      boolean lastElementRemoved = false;

      @Override
      public boolean hasNext() {
        return (counter < size - 1) && (size != 0);
      }

      @Override
      public T next() {
        if (lastElementRemoved || counter >= size - 1) {
          throw new NoSuchElementException("There is no such element");
        }
        return (T) elements[++counter];
      }

      @Override
      public void remove() {
        if (counter < 0) {
          throw new IllegalStateException(
              "There is no elements for removing, counter before fist element!");
        }
        if (counter == size - 1) {
          lastElementRemoved = true;
        }
        ArrayList.this.remove(counter);
        counter--;
      }
    };
  }

  private void resize() {
    Object[] temp = new Object[(int) (elements.length * GROWTH_FACTOR)];
    System.arraycopy(elements, 0, temp, 0, size);
    elements = temp;
  }
}
