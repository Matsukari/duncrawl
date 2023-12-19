package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.TilemapChara;

public class Tilemap {
  public final TerrainSet terrainSet;
  protected ArrayList<TilemapChara> chars = new ArrayList<>();
  public Tilemap(TerrainSet terrainSet) { 
    this.terrainSet = terrainSet;
    assert terrainSet != null;
  }
  public void render() {
    // for (TilemapChara chara : chars) {
    //   terrainSet.putToWorld(chara.chara, chara.x, chara.y);
    // }
    for (Terrain terrain : terrainSet.terrains) {
      if (terrain != null) terrain.render();
    }
    // for (int x = 0; x < terrainSet.cols; x++) {
    //   for (int y = 0; y < terrainSet.rows; y++) {
    //     Terrain terrain = terrainSet.getTerrain(x, terrainSet.rows - 1 - y);
    //     if (terrain != null) terrain.render();
    //   }
    // }
  }
  public void putChara(TilemapChara chara) {
    if (chars.contains(chara));
    chars.add(chara);
  }
  // These TilemapChara are supposed to start bottom-left; their axix is not aligned to a grid array
  public TilemapChara getChara(int x, int y) {
    for (TilemapChara chara : chars) {
      if (chara.onBlock(x, y) && !chara.chara.status.dead) return chara;
    }
    return null;
  }
}
