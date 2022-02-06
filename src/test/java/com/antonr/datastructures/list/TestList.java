package com.antonr.datastructures.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.antonr.datastructures.list.List;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class TestList {

  List<String> list = getList();

  protected abstract List<String> getList();

  @BeforeEach
  void setUp() {
    list.add("A");
    list.add("B");
  }

  @Test
  void addNullObjects() {
    list.add(null);
    assertNull(list.get(2));
    assertEquals("B", list.get(1));
  }

  @Test
  void addByIndex() {
    list.add("O");
    list.add("I");
    list.add("E", 2);
    list.add("K", 0);
    list.add("L", 1);
    assertEquals(0, list.indexOf("K"));
    assertEquals(1, list.indexOf("L"));
  }

  @Test
  void removeByIndex() {
    list.add("C");
    list.add("K");
    assertEquals(4, list.size());
    String removedElement = list.remove(2);
    assertEquals(3, list.size());
    assertEquals("C", removedElement);
    list.remove(0);
    assertEquals("B", list.get(0));
  }

  @Test
  void removingNotExistingElementByIndexShouldThrowIndexOutOfBounds() {
    list.remove(1);
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
  }

  @Test
  void removingNotExistingElementInEmptyList() {
    list.remove(1);
    list.remove(0);
    assertThrows(IllegalStateException.class, () -> list.remove(0));
  }

  @Test
  void getByIndex() {
    assertEquals("A", list.get(0));
    assertEquals("B", list.get(1));
  }

  @Test
  void setByIndex() {
    String oldElementWithIndex0 = list.set("O", 0);
    assertEquals("A", oldElementWithIndex0);
    assertEquals("O", list.get(0));
  }


  @Test
  void clear() {
    list.clear();
    assertEquals(0, list.size());
  }

  @Test
  void size() {
    list.add("C");
    assertEquals(3, list.size());
  }

  @Test
  void isEmpty() {
    assertFalse(list.isEmpty());
    list.clear();
    assertTrue(list.isEmpty());
  }

  @Test
  void contains() {
    assertTrue(list.contains("A"));
  }

  @Test
  void listDoesntContainSuchElement() {
    assertFalse(list.contains("L"));
  }

  @Test
  void indexOf() {
    list.add("A");
    assertEquals(0, list.indexOf("A"));
    assertEquals(-1, list.indexOf("Z"));
  }

  @Test
  void lastIndexOf() {
    list.add("A");
    assertEquals(2, list.lastIndexOf("A"));
    assertEquals(-1, list.indexOf("Z"));
  }

  @Test
  void noSuchElementForLastLastIndexOf() {
    assertEquals(-1, list.lastIndexOf("I"));
  }

  @Test
  void testToString() {
    list.clear();
    assertEquals("[]", list.toString());
    list.add("A");
    assertEquals("[A]", list.toString());
    list.add("B");
    assertEquals("[A, B]", list.toString());
  }

  @Test
  void gettingNonExistingElementInIterator() {
    list.add("A");
    Iterator<String> iterator = list.iterator();
    while (iterator.hasNext()) {
      if ("A".equals(iterator.next())) {
        iterator.remove();
      }
    }
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  void throwExceptionWhenIterationDidntStart() {
    Iterator<String> iterator = list.iterator();
    assertThrows(IllegalStateException.class, iterator::remove);
  }

}
