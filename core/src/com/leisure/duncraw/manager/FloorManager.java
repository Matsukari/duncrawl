package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Tilemap;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;
import com.leisure.duncraw.map.loader.TmxLoader;

public class FloorManager {
  public int level;
  private Floor floor;
  public final FloorData sources;
  public SpriteBatch batch = new SpriteBatch();
  public FloorManager(SaveData save, FloorData sources, int levelStart) {
    this.sources = sources;
    level = levelStart;
    floor = new Floor(TerrainSetGenerator.gen(level));
    floor.exits.addAll(TerrainSetGenerator.selectExits(floor.terrainSet));
    // TmxLoader.load(sources.startingHall, batch);
  }
  public void setCurrentFloor(Floor f) { floor = f; }
  public Floor getCurrentFloor() { return floor; }
  public void renderCurrent(Camera cam) {
    batch.setProjectionMatrix(cam.combined);
    batch.begin();
    floor.render();
    batch.end();
  }
}
