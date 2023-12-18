package com.leisure.duncraw.map;

import com.leisure.duncraw.art.map.LayeredTerrain;
import com.leisure.duncraw.art.map.Obj;
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
  public boolean isWithin(int x, int y) { return x >= 0 || y >= 0 || x < cols || y < rows; }
  public void putTerrain(Terrain terrain, int x, int y) { putTerrain(terrain, x, y, true); }
  public void putTerrain(Terrain terrain, int x, int y, boolean snapSize) {
    // Logger.log("TerrainSet", String.format("put terrain at %d %d", x, y));
    int cell = y * cols + x;
    if (snapSize) terrain.bounds.setSize(terrainWidth, terrainHeight);
    terrain.bounds.x = x * terrainWidth;
    terrain.bounds.y = y * terrainHeight;

    // Overlay then
    if (terrains[cell] == null) terrains[cell] = terrain;
    else if (terrains[cell] instanceof LayeredTerrain) ((LayeredTerrain)terrains[cell]).add(terrain);
    else terrains[cell] = new LayeredTerrain(terrain);
    // terrains[cell].getTail().next = terrain;  
  }
  public void putObject(Obj obj, int x, int y) { putObject(obj, x, y, true); }
  public void putObject(Obj obj, int x, int y, boolean snapSize) {
    if (getTerrain(x, y) != null) { 
      obj.bounds.x = x * terrainWidth;
      obj.bounds.y = y * terrainHeight;
      if (snapSize) obj.bounds.setSize(terrainWidth, terrainHeight);
      getTerrain(x, y).putObj(obj);
      Logger.log("TerrainSet", "put obj at" + obj.bounds.toString());
    }
    else 
      Logger.log("TerrainSet", "cannot put obj");
  }
  public int getTilesNum() { return cols * rows; }
  public Terrain getTerrain(int x, int y) { 
    try { return terrains[y*cols+x]; } 
    catch (Exception e) {}  
    return null;
  }
}
