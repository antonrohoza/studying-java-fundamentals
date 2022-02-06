package com.antonr.datastructures.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class HashMapTest {

  @Test
  void checkIntegerValues() {
    Map<Integer, Integer> map = new HashMap<>();
    map.put(1, 4);
    assertEquals(4, map.put(1, 2));
  }

  @Test
  void checkStringValues() {
    Map<String, String> map = new HashMap<>();
    map.put("1", "1");
    assertEquals("1", map.put("1", "3"));
  }

  @Test
  void checkResizeWithInitialCapacity() {
    Map<String, String> map = new HashMap<>(4);
    map.put("1", "1");
    map.put("2", "2");
    map.put("3", "3");
    map.put("4", "4");
    assertEquals("4", map.get("4"));
  }

  @Test
  void putWithCollisions() {
    // Elements with same hashcode() = 2019172
    String first = "AaAa";
    String second = "BBBB";
    String third = "AaBB";
    Map<String, Integer> map = new HashMap<>();
    map.put(first, 1);
    map.put(second, 2);
    map.put(third, 3);
    map.put(second, 4);
    assertEquals(1, map.get(first));
    assertEquals(4, map.get(second));
    assertEquals(3, map.get(third));
  }

  @Test
  void removeWithCollisions() {
    String first = "AaAa";
    String second = "BBBB";
    String third = "AaBB";
    Map<String, Integer> map = new HashMap<>();
    map.put(first, 1);
    map.put(second, 2);
    map.put(third, 3);
    map.remove(second);
    assertEquals(2, map.size());
    assertFalse(map.containsKey(second));
    assertEquals(1, map.get(first));
    assertEquals(3, map.get(third));
  }

  @Test
  void remove() {
    Map<String, String> map = new HashMap<>();
    map.put("1", "1");
    map.put("2", "2");
    map.remove("1");
    assertEquals(1, map.size());
  }

  @Test
  void gettingNonExistingElementInIterator() {
    Map<String, String> map = new HashMap<>();
    map.put("1", "1");
    map.put("2", "2");
    Iterator<Map.Entry<String, String>> iterator = map.iterator();
    while (iterator.hasNext()) {
      iterator.next().getValue();
    }
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  void entrySetCheck() {
    Map<String, String> map = new HashMap<>();
    map.put("1", "1");
    map.put("2", "2");
    assertEquals(2, map.entrySet().size());
    assertTrue(map.entrySet().remove(new HashMap.Entry<>("2", "2")));
    assertFalse(map.entrySet().contains(new HashMap.Entry<>("2", "2")));
  }
}