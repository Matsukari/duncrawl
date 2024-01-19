package com.leisure.duncraw.data;

import com.leisure.duncraw.art.item.Item;

public class InventoryItemData extends Dat {
  public String datFile;
  public String classname;
  public int quantity;
  public int maxQuantity;
  public InventoryItemData(Item item) {
    datFile = item.datFile;
    quantity = item.quantity;
    maxQuantity = item.maxQuantity;
    classname = item.getClass().getName();
  }
  public InventoryItemData() {}
  @Override
  public void reset() {
    quantity = 0;
    maxQuantity = 16;
    datFile = "none";
    classname = "none";
  }
  public boolean same(Object obj) {
    return ((InventoryItemData)obj).datFile.equals(datFile);
  }
  
}
