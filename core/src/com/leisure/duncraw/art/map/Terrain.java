package com.leisure.duncraw.art.map;

import java.util.ArrayList;
import java.util.TreeMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;

public class Terrain extends Art {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public ArrayList<Obj> objs;
  public TextureRegion texture;
  public Chara chara;
  private final SpriteBatch batch;
  public Terrain(SpriteBatch batch, TextureRegion texture) {
    assert batch != null && texture != null;
    this.batch = batch;
    this.texture = texture;
  }
  @Override
  public void render() {
    for (Obj obj : objs) obj.render();
    batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    if (chara != null) chara.render();
  }
}
