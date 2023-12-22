package com.leisure.duncraw.art.item.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;

public class StaminaPotion extends Item {
  public StaminaPotion(String datFile) {
    super(datFile);
  }
  @Override
  public void use() {
    owner.status.setStamina(owner.status.stamina + 19); 
  }
}
