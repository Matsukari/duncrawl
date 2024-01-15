package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.leisure.duncraw.helper.IdGenerator;

import lib.time.Timer;

public class InterpolationEffect implements Effect {
  public Interpolation interpolation;
  public float dur = 0;
  protected Timer timer;
  protected Art art;
  private int id;
  public InterpolationEffect(Art art, Interpolation interpolation, float sec) {
    this.interpolation = interpolation;
    this.art = art;
    timer = new Timer(sec * 1000);
    id = IdGenerator.gen();
  }
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof InterpolationEffect)) return false;
    return ((InterpolationEffect)obj).id == id;
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
    art.tint.a = 1 - interpolation.apply(timer.normalize());
  }
  @Override
  public boolean isFinished() {
    return timer.isFinished();
  } 
}
