package com.leisure.duncraw.manager;

import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Tilemap;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

public class FloorManager {
  private Floor floor;
  public int level;
  public FloorManager(SaveData save, FloorData sources, int levelStart) {
    level = levelStart;
    floor = new Floor(TerrainSetGenerator.gen(level));
    floor.exits.addAll(TerrainSetGenerator.selectExits(floor.terrainSet));
  }
  public void setCurrentFloor(Floor f) { floor = f; }
  public Floor getCurrentFloor() { return floor; }
}
