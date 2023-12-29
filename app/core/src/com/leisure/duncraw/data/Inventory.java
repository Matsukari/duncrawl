package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.logging.Logger;

public class Inventory extends Dat {
  public transient ArrayList<Item> items = new ArrayList<>();
  public transient Player owner;
  public ArrayList<InventoryItemData> itemsData;
  public int capacity;

  public Inventory() {}

  public Item select(int index) {
    try { return items.get(index); } catch(Exception e) {}
    return null;
  }
  public void put(Item item) {
    if (items.size() > capacity) return;
    item.isDrop = false; 
    item.owner = owner;
    InventoryItemData itemData =new InventoryItemData(item);;
    Logger.log("Inventory", "Put item " + item.id);
    if (!items.contains(item) || item.quantity > item.maxQuantity) {
      item = item.clone();
      items.add(item); 
      itemsData.add(itemData);
    }
    else {
      for (Item i : items) if (i.equals(item)) item = i;
      for (InventoryItemData i : itemsData) if (i.equals(itemData)) itemData = i; 
    }
    itemData.quantity++;
    item.quantity++;
  }
  public Item use(Item item) {
    item.use();
    if (item.quantity <= 0) {
      items.remove(item);
      itemsData.remove(new InventoryItemData(item));
      return null;
    } 
    return item;
  }
  public void insert(int index) {
  }
  public void merge() {
  }
  public void remove(int index) {
    items.remove(index);
  }
  // Cast into dynamic object, child of Item so it can use it's virtual method .use()
  public Inventory populate(Player owner) {
    this.owner = owner;
    for (InventoryItemData itemData : itemsData) {
      try {
        Item item = (Item)Class.forName(itemData.classname).getDeclaredConstructor(String.class).newInstance(itemData.datFile);
        item.quantity = itemData.quantity;
        item.maxQuantity = itemData.maxQuantity;
        item.owner = owner;
        items.add(item);
      } catch (Exception e) { e.printStackTrace(); System.exit(-1); }
    }
    return this;
  }
  @Override
  public void reset() {
    items = new ArrayList<>(); 
    itemsData = new ArrayList<>();
    capacity = 10;
  }
}
