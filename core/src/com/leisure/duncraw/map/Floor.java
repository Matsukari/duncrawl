package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.leisure.duncraw.art.map.Obj;

public class Floor extends Tilemap {
  public ArrayList<Obj> exits = new ArrayList<>();
  public int level;
  public Floor(TerrainSet terrainSet) { 
    super(terrainSet);
  }
  public static class Exit {
    public Floor next;
    public Obj obj;
  }

}
