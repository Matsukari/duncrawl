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
    if (chars.contains(chara));
    chars.add(chara);
  }
  public TilemapChara getChara(int x, int y) {
    for (TilemapChara chara : chars) {
      if (chara.onBlock(x, y)) return chara;
    }
    return null;
  }
}
