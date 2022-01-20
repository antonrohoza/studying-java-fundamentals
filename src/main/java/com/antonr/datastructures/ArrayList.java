package com.antonr.datastructures;

import java.util.Objects;

public class ArrayList implements List {

  private static final double GROWTH_FACTOR = 1.5;
  private static final int DEFAULT_CAPACITY = 10;
  private Object[] elements;
  private int size;

  public ArrayList(int initCapacity) {
    if (initCapacity <= 0) {
      throw new IllegalArgumentException();
    }
    elements = new Object[initCapacity];
  }

  public ArrayList() {
    elements = new Object[DEFAULT_CAPACITY];
  }

  private void resize() {
    Object[] temp = new Object[(int) (elements.length * GROWTH_FACTOR)];
    System.arraycopy(elements, 0, temp, 0, size);
    elements = temp;
  }

  @Override
  public void add(Object value) {
    add(value, size);
  }

  @Override
  public void add(Object value, int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0; size of the list]");
    }
    if (size == elements.length) {
      resize();
    }
    System.arraycopy(elements, index, elements, index + 1, size - index);
    elements[index] = value;
    size++;
  }

  @Override
  public Object remove(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0; size of the list]");
    }
    Object removedElement = elements[index];
    if (index < size - 1) {
      System.arraycopy(elements, index + 1, elements, index, size - index - 1);
    } else {
      elements[size - 1] = null;
    }
    size--;
    return removedElement;
  }

  @Override
  public Object get(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0; size of the list]");
    }
    return elements[index];
  }

  @Override
  public Object set(Object value, int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0; size of the list]");
    }
    Object previousValue = elements[index];
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

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public boolean contains(Object value) {
    return indexOf(value) != -1;
  }

  @Override
  public int indexOf(Object value) {
    for (int i = 0; i < size; i++) {
      if (Objects.equals(elements[i], value)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object value) {
    for (int i = size - 1; i >= 0; i--) {
      if (Objects.equals(elements[i], value)) {
        return i;
      }
    }
    return -1;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    for (int i = 0; i < size; i++) {
      sb.append(" ").append(elements[i]);
      if (i != size - 1) {
        sb.append(",");
      } else {
        sb.append(" ");
      }
    }
    return sb.append("]").toString();
  }
}
