package com.antonr.datastructures;

public enum ListUtils {
  ;

  static void checkIndex(int index, int size) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(
          "Wrong index, index must belong to the interval [0;" + (size - 1) + "]!");
    }
  }
}
