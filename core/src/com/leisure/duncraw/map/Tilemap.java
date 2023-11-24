package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.TilemapChara;

public class Tilemap {
  public final TerrainSet terrainSet;
  protected ArrayList<TilemapChara> chars = new ArrayList<>();
  public Tilemap(TerrainSet terrainSet) { 
    this.terrainSet = terrainSet;
  }
  public void render() {
    for (Terrain terrain : terrainSet.terrains) {
      if (terrain != null) terrain.render();
    }
  }
  public void putChara(TilemapChara chara) {
    if (chars.contains(chara)) return;
    chars.add(chara);
  }
}
