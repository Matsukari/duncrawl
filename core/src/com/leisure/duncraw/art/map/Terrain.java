package com.leisure.duncraw.art.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;

import lib.animation.LinearAnimation;

public class Terrain extends Art {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public ArrayList<Obj> objs = new ArrayList<>();
  public Terrain next = null;
  public LinearAnimation<TextureRegion> anim;
  public Terrain(SpriteBatch batch, LinearAnimation<TextureRegion> anim) {
    super(batch);
    this.anim = anim;
  }
  public boolean isWall() { return false; }
  public void putObj(Obj obj) { objs.add(obj); }
  public Terrain getTail() {
    Terrain node = this;
    while (node.next != null) node = node.next;
    return node;
  }
  public Obj lastObj() { 
    if (objs.isEmpty()) return null;
    return objs.get(objs.size()-1); 
  }
  @Override
  public void render() {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
    for (Obj obj : objs) obj.render();
    for (Terrain node = next; node != null; node = node.next) node.render();
  }
}
