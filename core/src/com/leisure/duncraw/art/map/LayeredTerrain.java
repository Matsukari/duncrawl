package com.leisure.duncraw.art.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;

public class LayeredTerrain extends Terrain {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  public ArrayList<Terrain> overlays = new ArrayList<>();
  public LayeredTerrain(Terrain base, Terrain ... overs) {
    super(null, null);
    for (Terrain terrain : overs) overlays.add(terrain);
  }
  @Override
  public void render() {
    for (Terrain terrain : overlays) terrain.render();
    super.render();
  }
}

