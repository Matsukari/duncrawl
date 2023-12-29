package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.map.Floor;

public class TilemapChara {
  public final Floor map;
  public final Chara chara;
  public int x = 0;
  public int y = 0;
  public TilemapChara(Chara chara, Floor map) {
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
     return map.background.getTerrain(x+byX, y+byY);
  }
  public Obj getObjBy(int byX, int byY) {
    // Terrain terrain = getTerrainBy(byX, byY);
    // if (terrain != null) return terrain.lastObj();
    return map.background.getObj(x+byX, y+byY);
  }
  public boolean onBlock(int u, int v) { return x == u && y == v; } 
  public int getWidth() { return map.background.terrainWidth; }
  public int getHeight() { return map.background.terrainHeight; }
}
