package com.antonr.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class ArrayListTest extends TestList {

  @Override
  protected List<String> getList() {
    return new ArrayList<>();
  }

  @Test
  void resizeCheck() {
    ArrayList<String> list = new ArrayList<>(2);
    list.add("A");
    list.add("B");
    list.add("C");
    assertEquals(3, list.capacity());
  }

  @Test
  void throwIllegalArgumentExceptionIfInappropriateInitialCapacity() {
    try {
      List<String> list = new ArrayList<>(-1);
      fail("Initial capacity should be at least 1");
    } catch (IllegalArgumentException ignored) {
    }
  }
}