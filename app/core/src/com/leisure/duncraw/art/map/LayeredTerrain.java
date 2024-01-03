package com.leisure.duncraw.art.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LayeredTerrain extends Terrain {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public ArrayList<Terrain> overlays = new ArrayList<>();
  public LayeredTerrain(Terrain ... overs) {
    super(null);
    for (Terrain terrain : overs) overlays.add(terrain);
  }
  public void add(Terrain over) { overlays.add(over); }
  public boolean containsWType(String type) {
    for (Terrain terrain : overlays)  if (terrain.type.contains(type)) return true;
    return false;
  }
  @Override
  public void setTravel(boolean v) {
    for (Terrain terrain : overlays) { terrain.canTravel = v; }
  }
  @Override
  public boolean traversable() {
    for (Terrain terrain : overlays) if (!terrain.traversable()) return false;
    return true;
  }
  @Override
  public Terrain clone() {
    return overlays.get(0).clone();
  }
  @Override
  public void render(SpriteBatch batch) {
    for (Terrain terrain : overlays) terrain.render(batch);
  }
}

