package com.leisure.duncraw.art.map;

import java.util.ArrayList;

public class LayeredTerrain extends Terrain {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public ArrayList<Terrain> overlays = new ArrayList<>();
  public LayeredTerrain(Terrain ... overs) {
    super(null, null);
    for (Terrain terrain : overs) overlays.add(terrain);
  }
  public void add(Terrain over) { overlays.add(over); }
  @Override
  public void putObj(Obj obj) {
    overlays.get(0).putObj(obj);
  }
  @Override
  public boolean traversable() {
    for (Terrain terrain : overlays) if (!terrain.traversable()) return false;
    return true;
  }
  @Override
  public Obj lastObj() {
    return overlays.get(0).lastObj();
  }
  @Override
  public Terrain clone() {
    return overlays.get(0).clone();
  }
  @Override
  public void render() {
    for (Terrain terrain : overlays) terrain.render();
  }
}

