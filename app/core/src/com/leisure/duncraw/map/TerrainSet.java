package com.leisure.duncraw.map;

import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.map.LayeredTerrain;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

import lib.math.Pointi;

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
  public boolean isWithin(int x, int y) { return x >= 0 && y >= 0 && x < cols && y < rows; }
  public void putTerrain(Terrain terrain, int x, int y) { putTerrain(terrain, x, y, true, false); }

  // The input (x, y) are in top-left (corresponding to the position in the array of terrains)
  public void putTerrain(Terrain terrain, int x, int y, boolean snapSize, boolean replace) {
    // Logger.log("TerrainSet", String.format("put terrain at %d %d", x, y));
    if (!isWithin(x, y)) return;
    int cell = y * cols + x;
    if (snapSize) terrain.bounds.setSize(terrainWidth, terrainHeight);
    terrain.setPosition(x * terrainWidth, y * terrainHeight);

    // Overlay then
    if (terrains[cell] == null || replace) terrains[cell] = terrain;
    else if (terrains[cell] instanceof LayeredTerrain) ((LayeredTerrain)terrains[cell]).add(terrain);
    else terrains[cell] = new LayeredTerrain(terrains[cell], terrain);
  }
  public void replaceTerrain(Terrain terrain, int x, int y) { putTerrain(terrain, x, y, true, true); }
  public void putObject(Obj obj, int x, int y) { putObject(obj, x, y, true); }
  public void putObject(Obj obj, int x, int y, boolean snapSize) {
    if (getTerrain(x, y) != null) { 
      if (snapSize) obj.bounds.setSize(terrainWidth, terrainHeight);
      getTerrain(x, y).putObj(obj);
      Logger.log("TerrainSet", "Put object at " + String.format("%d %d ", x, y) + SString.toString(obj.bounds));
    }
    else 
      Logger.log("TerrainSet", "cannot put obj");
  }
  public int getTilesNum() { return cols * rows; }
  public Terrain getTerrain(int x, int y) { 
    try { return terrains[y*cols+x]; } 
    catch (Exception e) { }  
    return null;
  }
  public Pointi getTerrainMapCoord(Terrain terrain) {
    return new Pointi((int)terrain.bounds.x/terrainWidth, rows - 1 - ((int)terrain.bounds.y/terrainHeight));
  }
}

