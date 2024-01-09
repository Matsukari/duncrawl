package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

import lib.time.Timer;

public abstract class InterpolationEffect implements Effect {
  public Interpolation interpolation;
  public float dur = 0;
  protected Timer timer;
  public InterpolationEffect(Interpolation interpolation, float sec) {
    this.interpolation = interpolation;
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
  }
  @Override
  public boolean isFinished() {
    return timer.isFinished();
  } 
}
