package com.antonr.datastructures.list;

class LinkedListTest extends TestList {

  @Override
  protected List<String> getList() {
    return new LinkedList<>();
  }
}