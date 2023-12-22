package com.leisure.duncraw.art.item.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.item.Item;

public class ExpansionBag extends Item {
  public ExpansionBag(String datFile) {
    super(datFile);
  }
  @Override
  public void use() {
    owner.inventory.capacity += 20;
  }
}
