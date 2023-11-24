package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.leisure.duncraw.art.item.Item;

public class Inventory {
  public ArrayList<Item> list;
  public Item current;
  public Inventory() {}
  // List operations
  public Item select(int index) {
    return null;
  }
  public void add(int index) {}
  public void insert(int index) {}
  public void merge() {}
  public void remove(int index) {}
  public void reset() {
    list = new ArrayList<>();
    current = null;
  }
}
