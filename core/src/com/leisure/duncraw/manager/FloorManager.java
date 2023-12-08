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
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.generator.FloorGenerator;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

public class FloorManager {
  public SpriteBatch batch = new SpriteBatch();
  public FloorGenerator floorGenerator;
  private FrameBuffer lightBuffer;
  private TextureRegion light;
  private Floor floor; 
  public int level;
  public final FloorData sources;
  public FloorManager(SaveData save, FloorData sources, int levelStart) {
    this.sources = sources;
    level = levelStart;
    floorGenerator = new FloorGenerator();
    floor = floorGenerator.gen();
    floor.exits.addAll(TerrainSetGenerator.selectExits(floor.terrainSet));
    lightBuffer = new FrameBuffer(Format.RGB888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    Graphics.assets.load("images/lights/light_smooth.png", Texture.class);
    Graphics.assets.finishLoadingAsset("images/lights/light_smooth.png");
    light = new TextureRegion(Graphics.assets.get("images/lights/light_smooth.png", Texture.class));
    batch.enableBlending();
    Gdx.gl.glEnable(GL20.GL_BLEND);

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
    batch.end();
  }
  public Color color = new Color(0.005f, 0.05f, 0.06f, 1f);
  public void renderForeground(Camera cam) {
    batch.setProjectionMatrix(cam.combined);
    batch.begin();
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    floor.renderForeground();
    batch.end();

    batch.begin();
    batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
    batch.draw(lightBuffer.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
    batch.end();

  }
}
