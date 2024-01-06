package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.ai.AiWanderer;
import com.leisure.duncraw.art.lighting.Lighting;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.FloorsData;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

public class FloorManager {
  public boolean showLighting = false;
  public final FloorsData sources;
  public final SpriteBatch batch;
  public Tileset tileset;
  private Floor floor;
  private RenderSortManager renderSortManager;
  private EffectManager effectManager;
  public Lighting lighting;
  
  public FloorManager(SaveData save, FloorsData sources, EffectManager effectManager, RenderSortManager renderSortManager) {
    this.sources = sources;
    this.renderSortManager = renderSortManager;
    this.effectManager = effectManager;

    batch = new SpriteBatch();
    tileset = new Tileset(sources.tilesets);
  }
  public void loadFloor(int level) { 
    Logger.log("FloorManager", String.format("Loading floor level %d", level) );
    FloorData data = Deserializer.safeLoad(FloorData.class, sources.floorsDat.get(level)); 
    data.level = level;
    if (floor != null) floor.unstage();
    TerrainSetGenerator terrainGenerator = new TerrainSetGenerator(sources.floorsDat.get(level), data, renderSortManager);
    try {
      floor = (Floor)Class.forName(data.classname).getDeclaredConstructor(TerrainSetGenerator.class).newInstance(terrainGenerator);
      if (lighting == null) 
        lighting = new Lighting(this);
    } catch (Exception e) { e.printStackTrace(); System.exit(-1); }
  }
  public void stageFloor(Player player, CharaManager charaManager) {
    Logger.log("FloorManager", "Staging floor...");
    if (floor.generator.data.firstGen) Logger.log("FloorManager", "...For the first time");
    floor.stage(player, tileset, effectManager);
    floor.initialSpawn(new EnemySpawner(charaManager, ()->new AiWanderer(floor, player)));
    lighting.updateEnv();
  }
  public void rebuild() {  

  }
  public int updateFloor() {
    return floor.nextLevel;
  }
  public Floor getFloor() { return floor; }
  public void renderBackground(Camera cam) {
    batch.setProjectionMatrix(cam.combined);
    batch.begin();
    floor.update();
    floor.render(batch, floor.background);
    batch.end();
    lighting.updateEnv();
  }
  public void renderForeground(Camera cam) {
    if (floor.foreground != null) {
      batch.setProjectionMatrix(cam.combined);
      batch.begin();
      floor.render(batch, floor.foreground);
      batch.end();
    }
    if (showLighting)
    {
      floor.lightEnvironment.update();
      floor.lightEnvironment.cast(cam);
    }
  }
  public void dispose() {
    batch.dispose();
  }
}
