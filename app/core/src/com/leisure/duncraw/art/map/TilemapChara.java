package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.Floor;

public class TilemapChara {
  private final FloorManager floorManager;
  public Floor map;
  public final Chara chara;
  public int x = 0;
  public int y = 0;
  public TilemapChara(Chara chara, FloorManager floorManager) {
    this.floorManager = floorManager;
    this.chara = chara;
    this.map = floorManager.getFloor();
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
  public void update() {
    map = floorManager.getFloor();
  }
  public int distance(TilemapChara other) {
    return Math.abs(x - other.x) + Math.abs(y - other.y);
  }
  public int dirDistance(TilemapChara other) {
    return 0;
  }
  public Terrain getTerrainBy(int byX, int byY) {
    update();
    return map.background.getTerrain(x+byX, y+byY);
  }
  public Obj getObjBy(int byX, int byY) {
    update();
    return map.background.getObj(x+byX, y+byY);
  }
  public boolean onBlock(int u, int v) { return x == u && y == v; } 
  public int getWidth() { update(); return map.background.terrainWidth; }
  public int getHeight() { update(); return map.background.terrainHeight; }
}
