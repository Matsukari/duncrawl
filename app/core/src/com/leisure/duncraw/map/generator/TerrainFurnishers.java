package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.map.TerrainSet;

public class TerrainFurnishers {
  public final ArrayList<TerrainFurnisher> furnishers = new ArrayList<>();
  public void add(TerrainFurnisher furnisher) {
    furnishers.add(furnisher);
  } 
  public void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y) {
    for (TerrainFurnisher terrainFurnisher : furnishers) {
      terrainFurnisher.furnish(terrainSet, roomsBuilder, terrain, x, y);
    }
  }
  
}
