package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.leisure.duncraw.art.map.Obj;

public class Floor extends Tilemap {
  public ArrayList<Obj> exits = new ArrayList<>();
  public Tilemap foreground; 
  public int level;
  public static class Exit {
    public Floor next;
    public Obj obj;
  }
  public void renderForeground() {
    foreground.render();
  }
  public Floor(TerrainSet terrainSet, TerrainSet foreground) { 
    super(terrainSet);
    this.foreground = new Tilemap(foreground);
  }
  public void genLights() {
  }
}
