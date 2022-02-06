package com.antonr.datastructures.map;

import java.util.AbstractSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class HashMap<K, V> implements Map<K, V>, Iterable<Map.Entry<K, V>> {

  private static final int DEFAULT_CAPACITY = 16;
  private static final float DEFAULT_LOAD_FACTOR = 0.75f;
  private Entry<K, V>[] buckets;
  private int size = 0;

  public HashMap() {
    this(DEFAULT_CAPACITY);
  }

  @SuppressWarnings("unchecked")
  public HashMap(int initialCapacity) {
    buckets = new Entry[initialCapacity];
  }

  // Similar to java7 implementation
  static class Entry<K, V> implements Map.Entry<K, V> {

    final K key;
    V value;
    Entry<K, V> next;

    public Entry(K key, V value) {
      this.key = key;
      this.value = value;
      this.next = null;
    }

    public Entry(K key, V value, Entry<K, V> next) {
      this.key = key;
      this.value = value;
      this.next = next;
    }

    @Override
    public K getKey() {
      return this.key;
    }

    @Override
    public V getValue() {
      return this.value;
    }

    @Override
    public V setValue(V value) {
      V oldValue = this.value;
      this.value = value;
      return oldValue;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
      return Objects.equals(e.getKey(), key) && Objects.equals(e.getValue(), value);
    }

    @Override
    public int hashCode() {
      return Objects.hashCode(key) ^ Objects.hashCode(value);
    }
  }

  @Override
  public V put(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    Entry<K, V> oldEntry = buckets[bucketIndex];
    Entry<K, V> newEntry = new Entry<>(key, value, null);
    // current bucket is empty
    if (oldEntry == null) {
      buckets[bucketIndex] = newEntry;
      size++;
      if (size == buckets.length * DEFAULT_LOAD_FACTOR) {
        resize();
      }
      return null;
    } else {
      // bucket has one element
      if (oldEntry.next == null) {
        // keys which were put to map were the same
        if (key.equals(oldEntry.getKey())) {
          buckets[bucketIndex] = newEntry;
        } else {
          oldEntry.next = newEntry;
          size++;
        }
        return oldEntry.getValue();
        // current entry in chain of collisions
      } else {
        Entry<K, V> currentEntry = oldEntry;
        while (currentEntry.next != null) {
          if (key.equals(currentEntry.getKey())) {
            return currentEntry.setValue(value);
          }
          currentEntry = currentEntry.next;
        }
        currentEntry.next = newEntry;
        size++;
        return currentEntry.getValue();
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void resize() {
    Entry<K, V>[] newArr = new Entry[buckets.length * 2];
    System.arraycopy(buckets, 0, newArr, 0, buckets.length);
    buckets = newArr;
  }

  private int getBucketIndex(K key) {
    if (key == null) {
      return 0;
    }
    // ensure that the result is non negative
    return (key.hashCode() & Integer.MAX_VALUE) % buckets.length;
  }

  @Override
  public V get(K key) {
    int bucketIndex = getBucketIndex(key);
    Entry<K, V> entry = buckets[bucketIndex];
    if (entry.next == null) {
      return entry.getValue();
    }
    Entry<K, V> currentEntry = entry;
    while (!currentEntry.getKey().equals(key)) {
      currentEntry = currentEntry.next;
    }
    return currentEntry.getValue();
  }

  @Override
  public V remove(K key) {
    int bucketIndex = getBucketIndex(key);
    Entry<K, V> oldEntry = buckets[bucketIndex];
    if (oldEntry.next == null) {
      buckets[bucketIndex] = null;
    } else {
      Entry<K, V> currentEntry = oldEntry;
      while (!currentEntry.next.getKey().equals(key)) {
        currentEntry = currentEntry.next;
      }
      if (currentEntry.next.next != null) {
        currentEntry.next = currentEntry.next.next;
      } else {
        currentEntry.next = null;
      }
    }
    size--;
    return oldEntry.getValue();
  }

  @Override
  public boolean containsKey(K key) {
    int hash = getBucketIndex(key);
    Entry<K, V> entry = buckets[hash];
    if (entry != null && entry.next != null) {
      while (entry != null) {
        if (key.equals(entry.getKey())) {
          return true;
        }
        entry = entry.next;
      }
    }
    return entry != null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    return new EntrySet();
  }

  @Override
  public java.util.Iterator<Map.Entry<K, V>> iterator() {
    return new Iterator();
  }

  class Iterator implements java.util.Iterator<Map.Entry<K, V>> {

    Entry<K, V> currentEntry;
    // field for easy check if next element exist
    Entry<K, V> nextEntry;
    int bucketIndex = 0;

    public Iterator() {
      while (bucketIndex < buckets.length) {
        if (buckets[bucketIndex] != null) {
          nextEntry = buckets[bucketIndex];
          break;
        }
        bucketIndex++;
      }
    }

    public boolean hasNext() {
      return nextEntry != null;
    }

    public Entry<K, V> next() {
      currentEntry = nextEntry;
      bucketIndex++;
      if (currentEntry == null) {
        throw new NoSuchElementException("There is no such element!");
      }
      while (bucketIndex < buckets.length) {
        if (nextEntry != null && nextEntry.next != null) {
          nextEntry = nextEntry.next;
          return currentEntry;
        }
        nextEntry = buckets[bucketIndex++];
        if (nextEntry != null) {
          return currentEntry;
        }
      }
      return currentEntry;
    }

    public void remove() {
      Entry<K, V> removingEntry = currentEntry;
      if (currentEntry == null) {
        throw new IllegalStateException(
            "There is no elements for removing, counter before fist element!");
      }
      currentEntry = null;
      HashMap.this.remove(removingEntry.getKey());
    }
  }

  private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {

    public java.util.Iterator<Map.Entry<K, V>> iterator() {
      return new Iterator();
    }

    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
      if (!(o instanceof Map.Entry)) {
        return false;
      }
      Map.Entry<K, V> e = (Map.Entry<K, V>) o;
      Entry<K, V> entry = buckets[getBucketIndex(e.getKey())];
      return entry != null && entry.equals(e);
    }

    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
      Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
      return HashMap.this.remove(entry.getKey()) != null;
    }

    public int size() {
      return size;
    }

    public void clear() {
      buckets = null;
    }
  }
}
