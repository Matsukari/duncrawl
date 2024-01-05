package com.leisure.duncraw.map.generator;


import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.map.TerrainSet;

public abstract class TerrainFurnisher {
  private int lastDec[];
  private int lastOffset[];
  protected TerrainFurnisher(int objs) { 
    lastDec = new int[objs];
    lastOffset = new int[objs];
  }
  protected TerrainFurnisher() {
    lastDec = new int[10];
    lastOffset = new int[10];
  }
  protected boolean genDistrib(int disX, int disY, int index) {
    if (lastDec[index] >= lastOffset[index]) {
      lastDec[index] = 0;
      lastOffset[index] = MathUtils.random(disX, disY);
      return true;
    }
    lastDec[index]++;
    return false;
  }

  public abstract void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y);
  public void start(TerrainSet terrainSet, RoomsBuilder roomsBuilder) {}
  public void finish(TerrainSet terrainSet, RoomsBuilder roomsBuilder) {}  
}
