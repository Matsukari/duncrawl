package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.art.item.Weapon;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Inventory;
import com.leisure.duncraw.data.SaveData;

public class Player extends Chara {
  public Inventory inventory = new Inventory();
  public Weapon weapon = null;
  public Player(CharaData data, SaveData saveData) {
    super(data);
    status = saveData.player.status;
    inventory = saveData.player.inventory.populate(this);
    movement.stepDuration = 3.5f;
    anims.get("move").setAnimDur(0.08f);
  }
  @Override
  public void update(float dt) {
    super.update(dt);
  }
}
