package com.leisure.duncraw.art.item;

import com.leisure.duncraw.data.ItemValuedData;
import com.leisure.duncraw.logging.Logger;

public class Weapon extends Item { 
  public int damage;
  public Weapon(String datFile) {
    load(datFile);
  }
  @Override
  public void load(String datFile) {
    ItemValuedData itemValuedData = loadType(datFile, ItemValuedData.class);
    damage = itemValuedData.value;
  }
  @Override
  public boolean use() {
    Logger.log("Weapon", "Equipp");
    owner.weapon = this;
    return true;
  }
}
