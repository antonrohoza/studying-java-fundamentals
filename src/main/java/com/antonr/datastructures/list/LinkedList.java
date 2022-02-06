package com.antonr.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<T> extends AbstractList<T> implements List<T>, Iterable<T> {

  private Node<T> head;
  private Node<T> tail;

  @Override
  public void add(T value, int index) {
    ListUtils.checkIndex(index, size + 1);
    Node<T> newNode = new Node<>(value);
    if (head == null) {
      head = tail = newNode;
    } else if (index == 0) {
      newNode.next = head;
      head.previous = newNode;
      head = newNode;
    } else if (index == size) {
      tail.next = newNode;
      newNode.previous = tail;
      tail = newNode;
    } else {
      Node<T> previousElement = getNodeByIndex(index - 1);
      newNode.previous = previousElement;
      previousElement.next = newNode;
      newNode.next = previousElement.next;
      previousElement.next.previous = newNode;
    }
    size++;
  }

  @Override
  public T remove(int index) {
    ListUtils.checkIndex(index, size);
    Node<T> removedElement;
    if (size == 1) {
      removedElement = head;
      head = tail = null;
    } else if (index == 0) {
      removedElement = head;
      head = head.next;
    } else if (index == size - 1) {
      removedElement = tail;
      tail = tail.previous;
    } else {
      Node<T> previousElement = getNodeByIndex(index - 1);
      removedElement = previousElement.next;
      previousElement.next = previousElement.next.next;
      previousElement.next.previous = previousElement;
    }
    size--;
    return removedElement.element;
  }

  @Override
  public T get(int index) {
    ListUtils.checkIndex(index, size);
    return getNodeByIndex(index).element;
  }

  @Override
  public T set(T value, int index) {
    ListUtils.checkIndex(index, size);
    Node<T> currentElement = getNodeByIndex(index);
    Node<T> oldElement = new Node<>(currentElement.element);
    currentElement.element = value;
    return oldElement.element;
  }

  @Override
  public void clear() {
    head = tail = null;
    size = 0;
  }

  @Override
  public boolean contains(T value) {
    Node<T> currentHead = head;
    Node<T> currentTail = tail;
    for (int i = 0; i < size / 2; i++) {
      if (Objects.equals(currentHead.element, value)
          || Objects.equals(currentTail.element, value)) {
        return true;
      }
      currentHead = currentHead.next;
      currentTail = currentTail.previous;
    }
    return false;
  }

  @Override
  public int indexOf(Object value) {
    Node<T> current;
    current = head;
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
    Node<T> current;
    current = tail;
    for (int i = size - 1; i >= 0; i--) {
      if (Objects.equals(current.element, value)) {
        return i;
      }
      current = current.previous;
    }
    return -1;
  }

  @Override
  public String toString() {
    StringJoiner sj = new StringJoiner(", ", "[", "]");
    Node<T> current = head;
    for (T element : this) {
      sj.add(String.valueOf(element));
      current = current.next;
    }
    return sj.toString();
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      Node<T> current = head;
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
        if (counter == -1) {
          current = head;
        } else {
          current = current.next;
        }
        counter++;
        return current.element;
      }

      @Override
      public void remove() {
        if (counter < 0) {
          throw new IllegalStateException(
              "There is no elements for removing, counter before fist element!");
        }
        current = current.previous;
        LinkedList.this.remove(counter);
        counter--;
      }
    };
  }

  private Node<T> getNodeByIndex(int index) {
    Node<T> current;
    if (index < size / 2) {
      current = head;
      for (int i = 0; i < index; i++) {
        current = current.next;
      }
    } else {
      current = tail;
      for (int i = size - 1; i > index; i--) {
        current = current.previous;
      }
    }
    return current;
  }

  private static class Node<T> {

    T element;
    Node<T> next;
    Node<T> previous;

    public Node(T element) {
      this.element = element;
    }

  }

}
