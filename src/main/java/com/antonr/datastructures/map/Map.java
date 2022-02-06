package com.antonr.datastructures.map;

import java.util.Iterator;
import java.util.Set;

public interface Map<K, V> {

  V put(K key, V value);

  V get(K key);

  V remove(K key);

  boolean containsKey(K key);

  int size();

  Set<Entry<K, V>> entrySet();

  Iterator<Entry<K, V>> iterator();

  interface Entry<K, V> {

    K getKey();

    V getValue();

    V setValue(V value);

    boolean equals(Object o);

    int hashCode();
  }
}
