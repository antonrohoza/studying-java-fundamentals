package com.antonr.datastructures;

import java.util.Objects;

public class LinkedList implements List {

  static class Node {

    Object element;
    Node next;

    public Node(Object element) {
      this.element = element;
    }
  }

  private Node first;
  private Node last;
  private int size;

  @Override
  public void add(Object value) {
    add(value, size);
  }

  private Node getPreviousElementByIndex(int index) {
    Node current = first;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current;
  }

  @Override
  public void add(Object value, int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0; size of the list]");
    }
    Node newNode = new Node(value);
    if (first == null) {
      first = last = newNode;
    } else if (index == 0) {
      newNode.next = first;
      first = newNode;
    } else if (index == size) {
      last.next = newNode;
      last = newNode;
    } else {
      Node currentNode = getPreviousElementByIndex(index + 1);
      newNode.next = currentNode.next;
      currentNode.next = newNode;
    }
    size++;
  }

  @Override
  public Object remove(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0; size of the list]");
    }
    Node removedElement;
    if (index == 0) {
      removedElement = first;
      first = first.next;
    } else if (index == size) {
      Node previousElement = getPreviousElementByIndex(index - 1);
      removedElement = previousElement.next;
      previousElement.next = null;
      last = previousElement;
    } else {
      Node previousElement = getPreviousElementByIndex(index - 1);
      removedElement = previousElement.next;
      previousElement.next = previousElement.next.next;
      last = previousElement.next;
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
    return getPreviousElementByIndex(index).element;
  }

  @Override
  public Object set(Object value, int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0; size of the list]");
    }
    Node currentElement = getPreviousElementByIndex(index);
    currentElement.element = value;
    return currentElement;
  }

  @Override
  public void clear() {
    first = last = null;
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
    Node current;
    current = first;
    for (int i = 0; i < size; i++) {
      if (Objects.equals(current.element, value)) {
        return i;
      }
      current = current.next;
    }
    return -1;
  }

  @Override
  public int lastIndexOf(Object value) {
    int lastIndex = -1;
    Node current;
    current = first;
    for (int i = 0; i < size; i++) {
      if (Objects.equals(current.element, value) && lastIndex < i) {
        lastIndex = i;
      }
      current = current.next;
    }
    return lastIndex;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Node current = first;
    for (int i = 0; i < size; i++) {
      sb.append(" ").append(current.element);
      current = current.next;
      if (i != size - 1) {
        sb.append(",");
      } else {
        sb.append(" ");
      }
    }
    return sb.append("]").toString();
  }
}
