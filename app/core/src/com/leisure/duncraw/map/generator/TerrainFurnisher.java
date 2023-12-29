package com.leisure.duncraw.map.generator;

import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.TerrainSet;

public abstract class TerrainFurnisher {
  // Context
  public abstract void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y);
  public void start(TerrainSet terrainSet, RoomsBuilder roomsBuilder) {}
  public void finish(TerrainSet terrainSet, RoomsBuilder roomsBuilder) {}  
}
