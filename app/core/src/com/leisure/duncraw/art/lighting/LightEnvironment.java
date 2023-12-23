package com.leisure.duncraw.art.lighting;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LightEnvironment {
  public final ArrayList<PointLight> lightSources = new ArrayList<>();
  private final SpriteBatch batch;
  private final FrameBuffer buffer;
  public Rectangle bounds;
  public float bufferWidth;
  public float bufferHeight;
  public Color envColor;
  public Viewport viewport;
  public LightEnvironment(Color envColor, Rectangle bounds, SpriteBatch batch) {
    this.batch = batch;
    this.bounds = bounds;
    this.envColor = envColor;
    
    float aspectRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
    float bufferSize = (int)Math.max(bounds.width, bounds.height);
    bufferWidth = bufferSize * aspectRatio;
    bufferHeight = bufferSize;
    // God, forgive me. I have fallen
    viewport = new ExtendViewport(bufferWidth, bufferHeight, new OrthographicCamera());
    viewport.update((int)bufferWidth, (int)bufferHeight, true);
    viewport.apply(true);
    buffer = new FrameBuffer(Format.RGB888, (int)bufferWidth, (int)bufferHeight, false);
    Gdx.gl.glEnable(GL20.GL_BLEND);
    batch.enableBlending();
  }
  public void update() {
    buffer.begin();
    Gdx.gl.glClearColor(envColor.r, envColor.g, envColor.b, envColor.a);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Draw to a static blank canvas 
    viewport.getCamera().update();
    batch.setProjectionMatrix(viewport.getCamera().combined);
    batch.begin();
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    for (PointLight light : lightSources) {
      light.render(batch);
    }
    batch.end();
    buffer.end();
  }
  public PointLight addLight(PointLight light) { lightSources.add(light); return light; }
  public PointLight removeLight(PointLight light) { lightSources.remove(light); return light; }
  public void cast(Camera camera) {
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
    batch.draw(buffer.getColorBufferTexture(), bounds.x, bounds.y, bufferWidth, bufferHeight, 0, 0, 1, 1);
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    batch.end();
  }
}
