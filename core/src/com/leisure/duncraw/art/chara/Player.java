package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Inventory;

public class Player extends Chara {
  public Inventory inventory = new Inventory();
  public Item itemSel;
  public Player(CharaData data, SpriteBatch batch) {
    super(data, batch);
    inventory.reset();
  }
  @Override
  public void update(float dt) {
    super.update(dt);
  }
  public void equip(Item item) {
    itemSel = item;
  }
}
