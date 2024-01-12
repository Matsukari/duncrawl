package com.leisure.duncraw.art.item;

import com.leisure.duncraw.data.ItemValuedData;
import com.leisure.duncraw.logging.Logger;

public class Armor extends Item { 
  public int defense;
  public Armor(String datFile) {
    load(datFile);
  }
  @Override
  public void load(String datFile) {
    ItemValuedData itemValuedData = loadType(datFile, ItemValuedData.class);
    defense = itemValuedData.value;
  }
  @Override
  public boolean use() {
    Logger.log("Armor", "Equipp");
    owner.armor = this;
    return true;
  }
}
