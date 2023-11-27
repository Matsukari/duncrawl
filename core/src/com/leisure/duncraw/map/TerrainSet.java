package com.leisure.duncraw.map;

import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;

public class TerrainSet {
  public int rows = 0, cols = 0;
  public int terrainWidth = 32, terrainHeight = 32;
  public Terrain terrains[] = {};
  public TerrainSet(int cols, int rows, int terrainWidth, int terrainHeight) { 
    this.cols = cols;
    this.rows  = rows;
    this.terrainWidth = terrainWidth;
    this.terrainHeight = terrainHeight;
    terrains = new Terrain[cols * rows];
    for (int i = 0; i < cols * rows; i++) terrains[i] = null;
  }
  public void putTerrain(Terrain terrain, int x, int y) {
    Logger.log("TerrainSet", "put terrain");
    int cell = y * cols + x; 
    terrain.bounds.x = x * terrainWidth;
    terrain.bounds.y = y * terrainHeight;

    // Overlay then
    if (terrains[cell] != null) terrains[cell].getTail().next = terrain; 
    else terrains[cell] = terrain;
    
  }
  public void putObject(Terrain terrain, int x, int y) {
    Logger.log("TerrainSet", "put obj");
  }
  public Terrain getTerrain(int x, int y) { return terrains[y*rows+x]; }
}

