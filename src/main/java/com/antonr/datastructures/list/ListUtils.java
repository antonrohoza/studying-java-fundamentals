package com.antonr.datastructures.list;

public enum ListUtils {
  ;

  static void checkIndex(int index, int size) {
    if ((index < 0 || index >= size) && size != 0) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0;" + (size - 1) + "]!");
    }
    if (size == 0) {
      throw new IllegalStateException(
          "You cannot do such manipulations with empty list");
    }
  }
}
