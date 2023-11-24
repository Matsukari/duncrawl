package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.map.Tilemap;

public class TilemapChara {
  public final Tilemap map;
  public final Chara chara;
  public int x;
  public int y;
  public TilemapChara(Chara chara,  Tilemap map) {
    this.chara = chara;
    this.map = map;
  }
  public void move(int nextX, int nextY) {
  }
  public int getWidth() { return map.terrainSet.terrainWidth; }
  public int getHeight() { return map.terrainSet.terrainHeight; }
  @Override
  public boolean equals(Object obj) {
    TilemapChara compare = (TilemapChara)obj;
    return (chara.id == compare.chara.id);
  }
}
