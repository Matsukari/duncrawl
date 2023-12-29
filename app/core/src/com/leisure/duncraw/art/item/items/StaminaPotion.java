package com.leisure.duncraw.art.item.items;

import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.ItemValuedData;

public class StaminaPotion extends Item {
  public int value;
  public StaminaPotion(String datFile) {
    load(datFile);
  }
  @Override
  public void load(String datFile) {
    ItemValuedData itemValuedData = loadType(datFile, ItemValuedData.class);
    value = itemValuedData.value;
  }
  public void onUse() {
    owner.status.setStamina(owner.status.stamina + value); 
  }

}
