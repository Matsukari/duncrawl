package com.leisure.duncraw.art.lighting;

import java.util.ArrayList;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leisure.duncraw.logging.Logger;

public class LightEnvironment {
  public final ArrayList<PointLight> lightSources = new ArrayList<>();
  private final SpriteBatch batch;
  private final FrameBuffer buffer;
  private final SpriteBatch own = new SpriteBatch();
  public Rectangle bounds;
  public float bufferWidth;
  public float bufferHeight;
  public Color envColor;
  public Viewport viewport;
  public LightEnvironment(Color envColor, Rectangle bounds, SpriteBatch batch) {
    this.batch = batch;
    this.bounds = bounds;
    this.envColor = envColor;
    // this.batch = new SpriteBatch();
    
    float aspectRatio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
    float bufferSize = (int)Math.max(bounds.width, bounds.height);
    bufferWidth = bufferSize * aspectRatio;
    bufferHeight = bufferSize;
    viewport = new ExtendViewport(bufferWidth, bufferHeight, new OrthographicCamera());
    viewport.update((int)bufferWidth, (int)bufferHeight, true);
    viewport.apply(true);
    // bufferWidth = Gdx.graphics.getWidth();
    // bufferHeight = Gdx.graphics.getHeight();
    buffer = new FrameBuffer(Format.RGB888, (int)bufferWidth, (int)bufferHeight, false);
    Logger.log("LightEnvironment", String.format("Size : %f %f", bufferWidth, bufferHeight));
    Gdx.gl.glEnable(GL20.GL_BLEND);
    batch.enableBlending();
  }
  float x = 0;
  public void update() {
    buffer.begin();
    Gdx.gl.glClearColor(envColor.r, envColor.g, envColor.b, envColor.a);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    // ScreenUtils.clear(envColor);
    viewport.getCamera().update();
    own.setProjectionMatrix(viewport.getCamera().combined);
    own.begin();
    own.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    x += 1;
    for (PointLight light : lightSources) {
      own.draw(light.textureRegion, x, 100, 100, 100);
      own.draw(light.textureRegion, 100, 100, 100, 100);
      // light.bounds.x += 1;
      light.render(own);
    }
    own.end();
    buffer.end();
  }
  public PointLight addLight(PointLight light) { lightSources.add(light); return light; }
  public PointLight removeLight(PointLight light) { lightSources.remove(light); return light; }
  public void cast(Viewport viewportd) {
    // batch.setProjectionMatrix(viewport.getCamera().combined);
    batch.begin();
    // batch.setProjectionMatrix(vviewpo)
    batch.setBlendFunction(GL20.GL_DST_COLOR, GL20.GL_ZERO);
    // batch.draw(buffer.getColorBufferTexture(), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight(), 0, 0, 1, 1);
    // batch.draw(buffer.getColorBufferTexture(), 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight(), 0, 0, 1, 1);
    batch.draw(buffer.getColorBufferTexture(), 0f, 0f, bufferWidth, bufferHeight, 0, 0, (int)bufferWidth, (int)bufferHeight, false, true);
    // batch.draw(buffer.getColorBufferTexture(), 0, 0, bufferWidth, bufferHeight);
    float cameraX = viewport.getCamera().position.x - viewport.getCamera().viewportWidth;
    float cameraY = viewport.getCamera().position.y + viewport.getCamera().viewportHeight;
    // batch.draw(buffer.getColorBufferTexture(), 
    //     0, 0, 
    //     bufferWidth, bufferHeight, (int)cameraX, (int)cameraY, viewport.getScreenWidth(), viewport.getScreenHeight(), false, true);
    // batch.draw(buffer.getColorBufferTexture(), 
    //     viewport.getCamera().position.x - viewport.getWorldWidth()*viewport.getCamera()/2, 
    //     viewport.getCamera().position.y + viewport.getWorldHeight()/2, 
    //     bufferWidth, bufferHeight, 0, 0, 1, 1);
    batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    batch.end();
  }
}
