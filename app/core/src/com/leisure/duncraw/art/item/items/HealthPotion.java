package com.leisure.duncraw.art.item.items;

import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ItemValuedData;

public class HealthPotion extends Item {
  public int value;
  public HealthPotion(String datFile) {
    super(datFile);
  }
  @Override
  public void load(String datFile) {
    ItemValuedData itemValuedData = loadType(datFile, ItemValuedData.class);
    value = itemValuedData.value; 
  }
  @Override
  public void onUse() {
    owner.status.setHealth(owner.status.health + value); 
  }
}
