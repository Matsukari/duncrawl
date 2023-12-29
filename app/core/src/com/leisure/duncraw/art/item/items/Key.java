package com.leisure.duncraw.art.item.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

public class Key extends Item {
  public Key(String datFile) {
    super(datFile);
  }
  @Override
  public void onUse() {
    // owner.status.setStamina(owner.status.stamina + 19); 
    Logger.log("Key", "Used");
  }
  @Override
  public void render(SpriteBatch batch) {
    super.render(batch);
    // Logger.log("Key", "Rendered at " + SString.toString(bounds));
  }
}
