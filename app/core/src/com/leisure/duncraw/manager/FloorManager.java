package com.leisure.duncraw.manager;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.FloorsData;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.map.generator.FloorGenerator;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

public class FloorManager {
  public SpriteBatch batch = new SpriteBatch();
  public FloorGenerator floorGenerator;
  public Tileset tileset;
  private FrameBuffer lightBuffer;
  private TextureRegion light;
  private Floor floor; 
  public int level;
  public final FloorsData sources;
  public FloorManager(SaveData save, FloorsData sources, int levelStart) {
    this.sources = sources;
    this.level = levelStart;
    
    tileset = new Tileset(sources.tilesets);
    FloorData floorData = new FloorData();
    floorData.reset();
    try { Deserializer.load(FloorData.class, Gdx.files.local(sources.floorsDat.get(level))); }
    catch(Exception e) { Serializer.save(floorData, Gdx.files.local(sources.floorsDat.get(level))); }
    floorGenerator = new FloorGenerator(floorData);
    floorGenerator.grounds = (tileset.terrainTransform(tileset.filter("terrain", "ground"), batch));
    floorGenerator.walls = (tileset.terrainTransform(tileset.filter("terrain", "wall"), batch));
    floor = floorGenerator.gen();
    floor.meta = floorGenerator.roomsBuilder;
    floor.exits.addAll(TerrainSetGenerator.selectExits(floor.terrainSet));

    lightBuffer = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    Graphics.assets.load("images/lights/light_smooth.png", Texture.class);
    Graphics.assets.finishLoadingAsset("images/lights/light_smooth.png");
    Gdx.gl.glEnable(GL20.GL_BLEND);
    light = new TextureRegion(Graphics.assets.get("images/lights/light_smooth.png", Texture.class));
    batch.enableBlending();
    lightBuffer.begin();
    ScreenUtils.clear(color);
    batch.begin();
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    batch.draw(light, 0, 0, 500, 500);
    batch.end();
    lightBuffer.end();

  }
  public void setCurrentFloor(Floor f) { floor = f; }
  public Floor getCurrentFloor() { return floor; }
  public void renderBackground(Camera cam) {
    batch.setProjectionMatrix(cam.combined);
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    batch.begin();
    floor.render();
    // floorGenerator.grounds.get(0).render(0, 0);
    batch.end();
  }
  public Color color = new Color(0.005f, 0.05f, 0.06f, 1f);
  public void renderForeground(Camera cam) {
    // batch.setProjectionMatrix(cam.combined);
    // batch.begin();
    // batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    // floor.renderForeground();
    // batch.end();

    // batch.begin();
    // batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
    // batch.draw(lightBuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
    // batch.end();

  }
  public void dispose() {
    batch.dispose();
  }
}
