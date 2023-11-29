package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.logging.Logger;

public class Inventory extends Dat {
  public ArrayList<Item> data;
  public Inventory() {}
  // List operations
  public Item select(int index) {
    return null;
  }
  public void put(Item item) {
    Logger.log("Inventory", "Put item " + item.id);
    data.add(item); 
    item.isDrop = false; 
  }
  public void insert(int index) {
  }
  public void merge() {
  }
  public void remove(int index) {
  }
  @Override
  public void reset() {
    data = new ArrayList<>(); 
  }
}
