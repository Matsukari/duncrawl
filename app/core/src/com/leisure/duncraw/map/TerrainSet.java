package com.leisure.duncraw.map;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import com.leisure.duncraw.art.EntityHashMap;
import com.leisure.duncraw.art.map.LayeredTerrain;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.helper.AArray;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.RenderSortManager;

import lib.math.Pointi;

public class TerrainSet {
  public int rows = 0, cols = 0;
  public int terrainWidth = 32, terrainHeight = 32;
  public Terrain terrains[] = {};
  public Pointi tph = new Pointi(); // TEmporary holder
  public EntityHashMap<Pointi, Obj> objs;

  public TerrainSet(int cols, int rows, int terrainWidth, int terrainHeight, RenderSortManager renderSortManager) { 
    this.cols = cols;
    this.rows  = rows;
    this.terrainWidth = terrainWidth;
    this.terrainHeight = terrainHeight;
    this.objs = new EntityHashMap<Pointi, Obj>(renderSortManager);
    terrains = new Terrain[cols * rows];
    for (int i = 0; i < cols * rows; i++) {
      terrains[i] = null;
    }
  }
  public boolean isWithin(int x, int y) { return x >= 0 && y >= 0 && x < cols && y < rows; }
  public void putTerrain(Terrain terrain, int x, int y) { putTerrain(terrain, x, y, true, false); }

  // The input (x, y) are in top-left (corresponding to the position in the array of terrains)
  public void putTerrain(Terrain terrain, int x, int y, boolean snapSize, boolean replace) {
    // Logger.log("TerrainSet", String.format("put terrain at %d %d", x, y));
    if (!isWithin(x, y)) return;
    int cell = y * cols + x;
    if (snapSize) terrain.bounds.setSize(terrainWidth, terrainHeight);
    terrain.bounds.setPosition(x * terrainWidth, y * terrainHeight);

    // Overlay then
    if (terrains[cell] == null || replace) terrains[cell] = terrain;
    else if (terrains[cell] instanceof LayeredTerrain) ((LayeredTerrain)terrains[cell]).add(terrain);
    else terrains[cell] = new LayeredTerrain(terrains[cell], terrain);
  }
  public void replaceTerrain(Terrain terrain, int x, int y) { putTerrain(terrain, x, y, true, true); }
  public void putObject(Obj obj, int x, int y) { putObject(obj, x, y, true); }
  public void putObject(Obj obj, int x, int y, boolean snapSize) {
    if (getTerrain(x, y) != null) { 
      // Expand and center to origin (x, y will still be topleft but interaction will be done in origin plus it's radius (block size)) 
      if (snapSize) {
        obj.bounds.setPosition(
            (x * terrainWidth) - (obj.dat.size.x-1) * terrainWidth/2, 
            (y * terrainHeight) - (obj.dat.size.y-1) * terrainHeight/2
            );
        obj.bounds.setSize(terrainWidth * obj.dat.size.x, terrainHeight * obj.dat.size.y);
      }
      if (!objs.data.containsKey(tph.set(x, y))) {
        objs.put(tph.set(x, y), obj);
      } else {
        Logger.log("TerrainSet", "Already has Obj");
      }
      // getTerrain(x, y).putObj(obj);
      // Logger.log("TerrainSet", "Put object at " + String.format("%d %d ", x, y) + SString.toString(obj.bounds));
    }
    else 
      Logger.log("TerrainSet", "cannot put obj");
  }
  public Obj getObj(int x, int y) {
    return objs.get(tph.set(x, y));
  }
  public <T extends Obj> AArray<T> getObj(Class<T> clazz) {
    AArray<T> list = new AArray<>();
    for (Map.Entry<Pointi, Obj> obj : objs.data.entrySet()) {
      if (obj.getValue().getClass() == clazz) list.add(clazz.cast(obj.getValue()));
      // Logger.log("getObj", "Checking " + obj.getValue().getClass().getName());
    }
    Logger.log("TerrainSet gotObj", String.format("%s: got %d", clazz.getSimpleName(), list.size()));
    return list;
  }
  public Terrain getTerrain(int x, int y) { 
    try { return terrains[y*cols+x]; } 
    catch (Exception e) { }  
    return null;
  }
  public float getWidth() { return cols * terrainWidth; }
  public float getHeight() { return rows * terrainHeight; }
  public int getTilesNum() { return cols * rows; }
  public Pointi getTerrainMapCoord(Terrain terrain) {
    return new Pointi((int)terrain.bounds.x/terrainWidth, rows - 1 - ((int)terrain.bounds.y/terrainHeight));
  }
}

