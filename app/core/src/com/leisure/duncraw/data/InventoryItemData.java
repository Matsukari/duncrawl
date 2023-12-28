package com.leisure.duncraw.data;

public class InventoryItemData extends Dat {
  public String datFile;
  public int quantity;
  public int maxQuantity;
  @Override
  public void reset() {
    quantity = 0;
    maxQuantity = 99;
    datFile = "none";
  }
  
}
