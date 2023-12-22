package com.leisure.duncraw.art.item.items;

import com.leisure.duncraw.art.item.Item;

public class HealthPotion extends Item {
  public HealthPotion(String datFile) {
    super(datFile);
  }
  @Override
  public void use() {
    owner.status.setHealth(owner.status.health + 10); 
  }
}
