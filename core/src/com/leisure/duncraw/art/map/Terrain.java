package com.leisure.duncraw.art.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;

public class Terrain extends Art {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public ArrayList<Obj> objs = new ArrayList<>();
  public Terrain next = null;
  public Terrain(SpriteBatch batch, TextureRegion texture) {
    super(batch, texture);
  }
  public Terrain getTail() {
    Terrain node = this;
    while (node.next != null) node = node.next;
    return node;
  }
  public void putObj(Obj obj) { objs.add(obj); }
  public Obj lastObj() { 
    if (objs.isEmpty()) return null;
    return objs.get(objs.size()); 
  }
  public boolean isWall() { return false; }
  @Override
  public void render() {
    super.render();
    for (Obj obj : objs) obj.render();
    for (Terrain node = next; node != null; node = node.next) node.render();
  }
}
