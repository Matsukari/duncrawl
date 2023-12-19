package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.map.Tilemap;

public class TilemapChara {
  public final Tilemap map;
  public final Chara chara;
  public int x = 0;
  public int y = 0;
  public TilemapChara(Chara chara,  Tilemap map) {
    this.chara = chara;
    this.map = map;
  }
  @Override
  public boolean equals(Object obj) {
    TilemapChara compare = (TilemapChara)obj;
    return (chara.id == compare.chara.id);
  }
  public void moveTo(int nextX, int nextY) {
    x = nextX;
    y = nextY;
  }
  public void moveBy(int byX, int byY) {
    x += byX;
    y += byY;
  }
  public int distance(TilemapChara other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y);
  }
  public int dirDistance(TilemapChara other) {
    return 0;
  }
  public Terrain getTerrainBy(int byX, int byY) {
     return map.terrainSet.getTerrain(x+byX, y+byY);
  }
  public Obj getObjBy(int byX, int byY) {
    Terrain terrain = getTerrainBy(byX, byY);
    if (terrain != null) return terrain.lastObj();
    return null;
  }
  public boolean onBlock(int u, int v) { return x == u && y == v; } 
  public int getWidth() { return map.terrainSet.terrainWidth; }
  public int getHeight() { return map.terrainSet.terrainHeight; }
}
