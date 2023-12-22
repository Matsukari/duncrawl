package com.leisure.duncraw.art.lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;

public class PointLight extends Art {
  public final TextureRegion textureRegion;
  public PointLight(TextureRegion textureRegion) {
    this.textureRegion = textureRegion;
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);
  }
  
}
