package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.FloorsData;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.TerrainVariants;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.map.WallType;
import com.leisure.duncraw.map.floors.Floor1;
import com.leisure.duncraw.map.generator.DeadBodiesFurnisher;
import com.leisure.duncraw.map.generator.LightSourcesFurnisher;
import com.leisure.duncraw.map.generator.MiscDecorationFurnisher;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

public class FloorManager {
  public final LightEnvironment lightEnvironment; 
  public final FloorsData sources;
  public final SpriteBatch batch;
  public Tileset tileset;
  public int level;
  private Floor floor; 
  public FloorManager(SaveData save, FloorsData sources, int levelStart, EffectManager effectManager) {
    this.sources = sources;
    this.level = levelStart;

    Logger.hide("RoomsBuilder");
    batch = new SpriteBatch();
    tileset = new Tileset(sources.tilesets);
    TerrainSetGenerator terrainGenerator = new TerrainSetGenerator(FloorData.fromDat(sources.floorsDat.get(level)));
    terrainGenerator.grounds = new TerrainVariants(tileset.terrainTransform(tileset.filter("terrain", "ground"), batch));
    terrainGenerator.walls = WallType.getAllWallTypes(tileset, batch);
    floor = new Floor1(terrainGenerator, batch); 
    lightEnvironment = new LightEnvironment(new Color(1, 1, 1, 1), new Rectangle(0, 0, floor.background.getWidth(), floor.background.getHeight()), batch);

    floor.generator.wallFurnishers.add(new LightSourcesFurnisher(lightEnvironment, effectManager));
    floor.generator.groundFurnishers.add(new DeadBodiesFurnisher());
    MiscDecorationFurnisher miscDecorationFurnisher = new MiscDecorationFurnisher();
    miscDecorationFurnisher.chests.addAll(Graphics.objsSources.floorChests.get(0));
    floor.generator.groundFurnishers.add(miscDecorationFurnisher);
    floor.stage();
    // Logger.log("FloorManager", String.format("Current floor (%d) name : %s", level, floor.getName()));
    // Logger.log("FloorManager", String.format("Floor data %s", level, floorData.title));
  }
  public void setCurrentFloor(Floor f) { floor = f; }
  public Floor getCurrentFloor() { return floor; }
  public void renderBackground(Camera cam) {
    batch.setProjectionMatrix(cam.combined);
    batch.begin();
    floor.update();
    floor.render(batch, floor.background);
    batch.end();
  }
  public void renderForeground(Camera cam) {
    if (floor.foreground != null) {
      batch.begin();
      floor.render(batch, floor.foreground);
      batch.end();
    }
    // lightEnvironment.update();
    // lightEnvironment.cast(cam);
  }
  public void dispose() {
    batch.dispose();
  }
}
