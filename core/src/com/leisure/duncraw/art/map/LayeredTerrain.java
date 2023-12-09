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
  public void render() {
    for (Terrain terrain : overlays) terrain.render();
  }
}

