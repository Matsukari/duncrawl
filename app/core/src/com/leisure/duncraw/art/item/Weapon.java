package com.leisure.duncraw.art.item;

import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ItemValuedData;

public class Weapon extends Item { 
  public int damage;
  public Weapon(String datFile) {
  }
  @Override
  public void load(String datFile) {
    ItemValuedData itemValuedData = loadType(datFile, ItemValuedData.class);
    damage = itemValuedData.value;
  }
}
