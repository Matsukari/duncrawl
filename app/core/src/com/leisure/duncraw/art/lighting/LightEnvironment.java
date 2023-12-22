package com.leisure.duncraw.art.lighting;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class LightEnvironment {
  public final ArrayList<PointLight> lightSources = new ArrayList<>();
  private final SpriteBatch batch;
  private final FrameBuffer buffer;
  public Rectangle bounds;
  public Color envColor;
  public LightEnvironment(Color envColor, Rectangle bounds, SpriteBatch batch) {
    this.batch = batch;
    this.bounds = bounds;
    this.envColor = envColor;
    // batch = new SpriteBatch();
    buffer = new FrameBuffer(Format.RGB888, (int)bounds.width, (int)bounds.height, false);
    Gdx.gl.glEnable(GL20.GL_BLEND);
    batch.enableBlending();
  }
  public void update() {
    buffer.begin();
    ScreenUtils.clear(envColor);
    batch.begin();
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    for (PointLight light : lightSources) light.render(batch);
    batch.end();
    buffer.end();

  }
  public void addLight(PointLight light) { lightSources.add(light); }
  public void removeLight(PointLight light) { lightSources.remove(light); }
  public void cast(SpriteBatch batch) {
    batch.begin();
    batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
    batch.draw(buffer.getColorBufferTexture(), 0, 0, bounds.width, bounds.height, 0, 0, 1, 1);
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    batch.end();
  }
}
