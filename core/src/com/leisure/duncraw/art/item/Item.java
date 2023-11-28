package com.leisure.duncraw.art.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.map.Obj;

public class Item extends Obj {
  public String desc;
  public boolean isDrop = false;
  public Item(SpriteBatch batch, TextureRegion texture) {
    super(batch, texture);
  }
  // Will be called by Terrain
  @Override
  public void render() {
  } 
  // Will be called by the UI
  public void renderStore() {
    
  }
}
