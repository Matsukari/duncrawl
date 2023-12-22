package com.leisure.duncraw.art.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.item.Item;

import lib.animation.LinearAnimation;

public class Terrain extends Art {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public final ArrayList<Obj> objs = new ArrayList<>();
  public LinearAnimation<TextureRegion> anim;
  public boolean canTravel = true;
  public String type = "none";
  public Terrain(LinearAnimation<TextureRegion> anim) {
    this.anim = anim;
  }
  public void putObj(Obj obj) {
    // obj.bounds.x = bounds.x;
    // obj.bounds.y = bounds.y;
    objs.add(obj); 
    if (!(obj instanceof Item)) canTravel = false;
  }
  public void setPosition(float x, int y) {
    for (Obj obj : objs) obj.bounds.setPosition(x, y);
    bounds.setPosition(x, y);
  }
  public boolean traversable() {
    return canTravel;
  }
  public Terrain clone() {
    Terrain terrain = new Terrain(anim);
    terrain.objs.addAll(objs);
    terrain.canTravel = canTravel;
    terrain.type = new String(type);
    return terrain;
  }
  public Obj lastObj() { 
    if (objs.isEmpty()) return null;
    return objs.get(objs.size()-1); 
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
    for (Obj obj : objs) {
      obj.bounds.x = bounds.x;
      obj.bounds.y = bounds.y;
      obj.bounds.setSize(bounds.width, bounds.height);
      obj.render(batch);
    }
  }
}
