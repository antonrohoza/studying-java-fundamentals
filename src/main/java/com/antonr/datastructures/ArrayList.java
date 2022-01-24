package com.antonr.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> extends AbstractList<T> implements List<T>, Iterable<T> {

  private static final double GROWTH_FACTOR = 1.5;
  private static final int DEFAULT_CAPACITY = 10;
  private T[] elements;

  public ArrayList() {
    this(DEFAULT_CAPACITY);
  }

  @SuppressWarnings("unchecked")
  public ArrayList(int initialCapacity) {
    if (initialCapacity > 0) {
      elements = (T[]) new Object[initialCapacity];
    } else {
      throw new IllegalArgumentException("Initial capacity should be at least 1");
    }
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
  public T remove(int index) {
    ListUtils.checkIndex(index, size);
    T removedElement = elements[index];
    if (index < size - 1) {
      System.arraycopy(elements, index + 1, elements, index, size - index - 1);
    }
    size--;
    elements[size] = null;
    return removedElement;
  }

  @Override
  public T get(int index) {
    ListUtils.checkIndex(index, size);
    return elements[index];
  }

  @Override
  public T set(T value, int index) {
    ListUtils.checkIndex(index, size);
    T previousValue = elements[index];
    elements[index] = value;
    return previousValue;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    elements = (T[]) new Object[DEFAULT_CAPACITY];
    size = 0;
  }

  public int capacity() {
    return elements.length;
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
    for (T element : this) {
      sj.add(String.valueOf(element));
    }
    return sj.toString();
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      int counter = -1;

      @Override
      public boolean hasNext() {
        return (counter < size - 1) && (size != 0);
      }

      @Override
      public T next() {
        if (counter >= size - 1) {
          throw new NoSuchElementException("There is no such element");
        }
        return elements[++counter];
      }

      @Override
      public void remove() {
        if (counter < 0) {
          throw new IllegalStateException(
              "There is no elements for removing, counter before fist element!");
        }
        ArrayList.this.remove(counter);
        counter--;
      }
    };
  }

  @SuppressWarnings("unchecked")
  private void resize() {
    T[] temp = (T[]) new Object[(int) (elements.length * GROWTH_FACTOR)];
    System.arraycopy(elements, 0, temp, 0, size);
    elements = temp;
  }
}
