package com.leisure.duncraw.art.item.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

public class StaminaPotion extends Item {
  public StaminaPotion(String datFile) {
    super(datFile);
  }
  @Override
  public void onUse() {
    owner.status.setStamina(owner.status.stamina + 19); 
    Logger.log("StaminaPotion", "Used");
  }
  @Override
  public void render(SpriteBatch batch) {
    super.render(batch);
    // Logger.log("StaminaPotion", "Rendered at " + SString.toString(bounds));
  }
}
