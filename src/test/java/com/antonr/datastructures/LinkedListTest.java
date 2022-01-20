package com.antonr.datastructures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LinkedListTest {

  List list;

  @BeforeEach
  void setUp() {
    list = new LinkedList();
  }

  @DisplayName("Add nulls")
  @Test
  void addNullObjects() {
    list.add(null);
    list.add("K");
    assertNull(list.get(0));
    assertEquals("K", list.get(1));
  }

  @DisplayName("Add by index")
  @Test
  void addByIndex() {
    list.add("A", 0);
    list.add("B", 1);
    assertEquals(0, list.indexOf("A"));
    assertEquals(1, list.indexOf("B"));
  }

  @DisplayName("Remove by index")
  @Test
  void removeByIndex() {
    list.add("A");
    list.add("B");
    list.add("C");
    list.remove(1);
    list.remove(0);
    assertEquals("C", list.get(0));
  }

  @DisplayName("Removing not existing element should throw IndexOutOfBounds exception")
  @Test
  void removingNotExistingElementByIndexShouldThrowIndexOutOfBounds() {
    list.add("A");
    list.add("B");
    list.remove(1);
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
  }

  @DisplayName("Get element by index")
  @Test
  void getByIndex() {
    list.add("A");
    list.add("B");
    assertEquals("A", list.get(0));
    assertEquals("B", list.get(1));
  }

  @DisplayName("Set element by index")
  @Test
  void setByIndex() {
    list.add("A");
    list.add("B");
    list.set("K", 0);
    list.set("L", 1);
    assertEquals("K", list.get(0));
    assertEquals("L", list.get(1));
  }


  @Test
  void clear() {
    list.add("A");
    list.add("B");
    list.clear();
    assertEquals(0, list.size());
  }

  @Test
  void size() {
    list.add("A");
    list.add("B");
    list.add("C");
    assertEquals(3, list.size());
  }

  @Test
  void isEmpty() {
    list.add("A");
    assertFalse(list.isEmpty());
    list.clear();
    assertTrue(list.isEmpty());
  }

  @Test
  void contains() {
    list.add("A");
    list.add("B");
    assertTrue(list.contains("A"));
  }

  @Test
  void indexOf() {
    list.add("A");
    list.add("B");
    list.add("A");
    assertEquals(0, list.indexOf("A"));
    assertEquals(-1, list.indexOf("Z"));
  }

  @Test
  void lastIndexOf() {
    list.add("A");
    list.add("B");
    list.add("A");
    assertEquals(2, list.lastIndexOf("A"));
    assertEquals(-1, list.indexOf("Z"));
  }

  @Test
  void testToString() {
    assertEquals("[]", list.toString());
    list.add("A");
    assertEquals("[ A ]", list.toString());
    list.add("B");
    assertEquals("[ A, B ]", list.toString());
  }

}