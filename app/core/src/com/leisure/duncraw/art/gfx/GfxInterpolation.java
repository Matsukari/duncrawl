package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.logging.Logger;

import lib.time.Timer;

public class GfxInterpolation extends Gfx {
  public Interpolation interpolation;
  public float dur = 0;
  protected Timer timer;
  public Art art;
  public GfxInterpolation(Art art, Interpolation interpolation, float sec) {
    this.interpolation = interpolation;
    this.art = art;
    timer = new Timer(sec * 1000);
  }
  @Override
  public void stop() {
    timer.stop();
  }
  @Override
  public void start() {
    timer.start();
  }
  @Override
  public void update(float dt) {
    // Logger.log("GfxInterpolation", "ALpha : " + Float.toString(art.tint.a));
    art.tint.a = 1 - interpolation.apply(timer.normalize());
  }
  @Override
  public void render(SpriteBatch batch) {
    art.render(batch);
  }
  @Override
  public boolean isFinished() {
    return timer.isFinished();
  } 
}
