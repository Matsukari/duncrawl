package com.leisure.duncraw.art.item.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

public class MonsterCore extends Item {
  public MonsterCore(String datFile) {
    super(datFile);
    Logger.log("MonsterCore", "Got " + Float.toString(anim.stateTime));
  }
  @Override
  public void onUse() {
    // owner.status.setStamina(owner.status.stamina + 19); 
    Logger.log("MonsterCore", "Used");
  }
}
