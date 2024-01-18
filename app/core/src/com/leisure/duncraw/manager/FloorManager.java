package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.leisure.duncraw.art.chara.Spawner;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.ai.AiWanderer;
import com.leisure.duncraw.art.gfx.GfxFloorTransition;
import com.leisure.duncraw.art.lighting.Lighting;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.FloorsData;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;
import com.leisure.duncraw.screen.GameScreen.Context;

import lib.time.Timer;

public class FloorManager {
  public boolean showLighting = false;
  public final FloorsData sources;
  public final SpriteBatch batch;
  public Tileset tileset;
  private Floor floor;
  private RenderSortManager renderSortManager;
  public Lighting lighting;
  private GfxFloorTransition transition;
  public boolean ready = false;
  
  public FloorManager(SaveData save, FloorsData sources, RenderSortManager renderSortManager) {
    this.sources = sources;
    this.renderSortManager = renderSortManager;

    batch = new SpriteBatch();
    tileset = new Tileset(sources.tilesets);
  }
  public void loadFloor(int level) { 
    Logger.log("FloorManager", String.format("Loading floor level %d", level) );
    FloorData data = Deserializer.safeLoad(FloorData.class, sources.floorsDat.get(level)); 
    data.level = level;
    int prevFloor = -1;
    if (floor != null) {
      prevFloor = floor.generator.data.level;
      floor.unstage();
    }
    TerrainSetGenerator terrainGenerator = new TerrainSetGenerator(sources.floorsDat.get(level), data, renderSortManager);
    try {
      floor = (Floor)Class.forName(data.classname).getDeclaredConstructor(TerrainSetGenerator.class).newInstance(terrainGenerator);
      floor.prevFloor = prevFloor;
      if (lighting == null) 
        lighting = new Lighting(this);
    } catch (Exception e) { e.printStackTrace(); System.exit(-1); }
  }
  public void stageFloor(Player player, CharaManager charaManager, Context context) {
    Logger.log("FloorManager", "Staging floor...");
    if (floor.generator.data.firstGen) Logger.log("FloorManager", "...For the first time");
    floor.stage(player, tileset, context);
    floor.initialSpawn(new Spawner(charaManager));
    lighting.updateEnv();
    ready = false;
    transition = new GfxFloorTransition(Graphics.getSafeTextureRegion("images/ui/diamond.png"), Interpolation.fade, 1f, player.bounds.x, player.bounds.y);
    transition.onCovered = ()-> {
      ready = true;
    };
    context.effectManager.start(transition);
  }
  public void rebuild() {  

  }
  public int updateFloor() {
    return floor.nextLevel;
  }
  public Floor getFloor() { return floor; }
  public void renderBackground(Camera cam) {
    if (!ready) return;
    batch.setProjectionMatrix(cam.combined);
    batch.begin();
    floor.update();
    floor.render(batch, floor.background);
    batch.end();
    lighting.updateEnv();
  }
  public void renderForeground(Camera cam) {
    if (!ready) return;
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
    if (floor != null) floor.unstage();
    batch.dispose();
  }
}
