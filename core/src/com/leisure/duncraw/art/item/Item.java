package com.leisure.duncraw.art.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.map.Obj;

public class Item extends Obj {
  public String desc;
  public interface Action {
    public void act();
  }
  public Item(SpriteBatch batch, TextureRegion texture) {
    super(batch, texture);
  }
  @Override
  public void render() {
  }
}
