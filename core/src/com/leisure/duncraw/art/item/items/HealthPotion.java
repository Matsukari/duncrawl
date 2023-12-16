package com.leisure.duncraw.art.item.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;

public class HealthPotion extends Item {
  public HealthPotion(SpriteBatch batch, String datFile) {
    super(batch, datFile);
  }
  @Override
  public void use() {
    owner.status.setHealth(owner.status.health + 10); 
  }
}
