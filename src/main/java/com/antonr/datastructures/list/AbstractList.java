package com.antonr.datastructures.list;

public abstract class AbstractList<T> implements List<T> {

  protected int size;

  @Override
  public void add(T value) {
    add(value, size);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

}
