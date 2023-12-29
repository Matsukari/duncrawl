package com.leisure.duncraw.data;

public class ItemValuedData extends ItemData {
  public int value;
  @Override
  public void reset() {
    super.reset();
    value = 1;
  }
}
