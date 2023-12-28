package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Inventory;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;

public class Player extends Chara {
  public Inventory inventory = new Inventory();
  public Item itemSel = null;
  public Player(CharaData data, SaveData saveData) {
    super(data);
    status = saveData.player.status;
    inventory = saveData.player.inventory;
    for (Item item : inventory.items) item.load(item.datFile);
    movement.stepDuration = 3.5f;
    anims.get("move").setAnimDur(0.08f);
  }
  @Override
  public void update(float dt) {
    super.update(dt);
  }
  public void equip(Item item) {
    Logger.log("Player", "Trying to equip");
    itemSel = item;
    Logger.log("Player", "Equipped");
  }
}
