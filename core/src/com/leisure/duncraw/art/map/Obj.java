package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.Art;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Obj extends Art {
  public TextureRegion texture;
  private final SpriteBatch batch;
  public Obj(SpriteBatch batch, TextureRegion texture) {
    this.batch = batch;
    this.texture = texture;
  }
  @Override
  public void render() {
    batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
  }
}
