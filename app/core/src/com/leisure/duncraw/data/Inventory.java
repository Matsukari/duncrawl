package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.logging.Logger;

public class Inventory extends Dat {
  public ArrayList<Item> items;
  // public ArrayList<InventoryItemData> itemsData;
  public int capacity;
  public Inventory() {}
  // List operations
  public Item select(int index) {
    return null;
  }
  public void put(Item item) {
    if (items.size() > capacity) return;
    Logger.log("Inventory", "Put item " + item.id);
    if (!items.contains(item) || item.quantity > item.maxQuantity) {
      item = item.clone();
      items.add(item); 
    }
    else {
      for (Item i : items) if (i.equals(item)) item = i;
    }
    item.quantity++;
    item.isDrop = false; 
  }
  public void insert(int index) {
  }
  public void merge() {
  }
  public void remove(int index) {
    items.remove(index);
  }
  // public Inventory populate() {
  //   for (InventoryItemData itemData : itemsData) {
  //     Item item = new Item(itemData.datFile);
  //     item.quantity = itemData.quantity;
  //     item.maxQuantity = itemData.maxQuantity;
  //     items.add(item);
  //   }
  //   return this;
  // }
  @Override
  public void reset() {
    items = new ArrayList<>(); 
    // itemsData = new ArrayList<>();
    capacity = 10;
  }
}
