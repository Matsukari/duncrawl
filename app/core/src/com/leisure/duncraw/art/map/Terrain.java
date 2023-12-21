package com.leisure.duncraw.art.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.item.Item;

import lib.animation.LinearAnimation;

public class Terrain extends Art {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public ArrayList<Obj> objs = new ArrayList<>();
  public LinearAnimation<TextureRegion> anim;
  public boolean canTravel = true;
  public String type = "none";
  public Terrain(SpriteBatch batch, LinearAnimation<TextureRegion> anim) {
    super(batch);
    this.anim = anim;
  }
  public void putObj(Obj obj) { 
    objs.add(obj); 
    if (!(obj instanceof Item)) canTravel = false;
  }
  public boolean traversable() {
    return canTravel;
  }
  public Terrain clone() {
    Terrain terrain = new Terrain(batch, anim);
    terrain.canTravel = canTravel;
    terrain.objs.addAll(objs);
    terrain.type = new String(type);
    return terrain;
  }
  public Obj lastObj() { 
    if (objs.isEmpty()) return null;
    return objs.get(objs.size()-1); 
  }
  @Override
  public void render() {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
    for (Obj obj : objs) obj.render();
  }
  public void render(int x, int y) {
    bounds.x = x;
    bounds.y = y;
    render();
  }
}
