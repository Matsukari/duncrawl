package com.leisure.duncraw.art.item.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

public class RawMeat extends Item {
  public RawMeat(String datFile) {
    super(datFile);
  }
  @Override
  public void onUse() {
    owner.status.setStamina(owner.status.stamina + 10); 
    owner.status.setStamina(owner.status.health + 8); 
    Logger.log("RawMeat", "Used");
  }
}

