package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.helper.Performer;

import lib.time.Timer;

public class GfxFloorTransition extends Gfx {
  public TextureRegion texture;
  public Interpolation interpolation;
  public Timer timer1;
  public Timer timer2;
  public float x, y;
  public Performer onCovered;
  public boolean once = false;
  public float size = Gdx.graphics.getWidth() + Gdx.graphics.getWidth() * 0.8f;
  public GfxFloorTransition(TextureRegion texture, Interpolation interpolation, float sec, float x, float y) {
    timer1 = new Timer(sec * 1000);
    timer2 = new Timer(sec * 1000);
    this.texture = texture;
    this.interpolation = interpolation;
    this.x = x;
    this.y = y;
  }
  @Override
  public void start() {
    timer1.start();
  }
  @Override
  public void stop() {
    timer1.stop();
    timer2.stop();
  }
  public boolean hasCoveredScreen() {
    return timer1.isFinished();
  }
  @Override
  public boolean isFinished() {
    return timer2.isFinished();
  }
  @Override
  public void update(float dt) {
    if (hasCoveredScreen()) {
      if (onCovered != null) {
        onCovered.perform();
        onCovered = null;
        timer2.start(); 
      }
      bounds.setSize((1 - interpolation.apply(timer2.normalize())) * size);
    }
    else {
      bounds.setSize(interpolation.apply(timer1.normalize()) * size);
    }
    bounds.setCenter(x, y);
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.setColor(tint);
    batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    batch.setColor(Color.WHITE);
  }

  
}
