package com.leisure.duncraw.data;

import java.nio.InvalidMarkException;
import java.util.ArrayList;

import com.badlogic.gdx.math.Interpolation.Exp;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.item.Armor;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.art.item.Weapon;
import com.leisure.duncraw.helper.AArray;
import com.leisure.duncraw.logging.Logger;

public class Inventory extends Dat {
  public transient AArray<Item> items = new AArray<>();
  public transient Player owner;
  public ArrayList<InventoryItemData> itemsData;
  public int capacity;
  public InventoryItemData armor; // include this i serialization
  public InventoryItemData weapon;

  public Inventory() {}

  public Item select(int index) {
    try { return items.get(index); } catch(Exception e) {}
    return null;
  }
  public void put(Item item) {
    if (items.size() > capacity) return;
    item.isDrop = false; 
    item.owner = owner;
    final Item testItem = item;
    InventoryItemData itemData =new InventoryItemData(item);;
    Logger.log("Inventory", "Put item " + item.id);
    if (items.getIf((e)->e.same(testItem)) == null || item.quantity > item.maxQuantity) {
      item = item.clone();
      items.add(item); 
      itemsData.add(itemData);
    }
    else {
      for (Item i : items) if (i.same(item)) item = i;
      for (InventoryItemData i : itemsData) if (i.same(itemData)) itemData = i; 
    }
    itemData.quantity++;
    item.quantity++;
  }
  private InventoryItemData getItemData(Item item) {
    InventoryItemData itemData =new InventoryItemData(item);;
    for (InventoryItemData i : itemsData) if (i.same(itemData)) itemData = i; 
    return itemData;
  }
  public Item use(Item item) {
    if (item instanceof Armor) {
      armor = new InventoryItemData(item);
      owner.armor = (Armor)item;
    }
    else if (item instanceof Weapon) {
      weapon = new InventoryItemData(item);
      owner.weapon = (Weapon)item;
    }
    item.use();
    getItemData(item).quantity = item.quantity;
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
    try {
      owner.weapon = (Weapon)Class.forName(weapon.classname).getDeclaredConstructor(String.class).newInstance(weapon.datFile);
      owner.armor = (Armor)Class.forName(armor.classname).getDeclaredConstructor(String.class).newInstance(armor.datFile);
    } catch (Exception e) { e.printStackTrace(); }
    return this;
  }
  @Override
  public void reset() {
    items = new AArray<>(); 
    itemsData = new ArrayList<>();
    capacity = 10;
  }
}
