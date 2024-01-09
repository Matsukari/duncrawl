package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;

public class TransparencyLerpEffect extends InterpolationEffect {
  private Art art;
  public TransparencyLerpEffect(Art art, Interpolation interpolation, float sec) { 
    super(interpolation, sec);
    this.art = art;
  }
  @Override
  public void update(float dt) {
    art.tint.a = interpolation.apply(timer.normalize());
  }
  
}
