package com.leisure.duncraw.art.effs;

import com.badlogic.gdx.math.Interpolation;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.InterpolationEffect;
import com.leisure.duncraw.logging.Logger;

public class KnockbackEffect extends InterpolationEffect {
  private float lastX, lastY;
  private float x, y;
  private boolean returned = false;
  public KnockbackEffect(Art art, float x, float y, Interpolation interpolation, float sec) {
    super(art, interpolation, sec);
    this.x = x;
    this.y = y;
    lastX = art.bounds.x;
    lastY = art.bounds.y;
    
    Logger.log("KnockbackEffect", String.format("Interp: %f %f", x, y));
  }
  @Override
  public void start() {
    super.start();
    returned = false;
  }
  @Override
  public void update(float dt) {
    if (x != 0) art.bounds.x = lastX + (interpolation.apply(timer.normalize()) * x); 
    if (y != 0) art.bounds.y = lastY + (interpolation.apply(timer.normalize()) * y);
    if (timer.isFinished() && !returned) {
      returned = true;
      timer.start();
      x *= -1;
      y *= -1;
      lastX = art.bounds.x;
      lastY = art.bounds.y;
    }
  }


  
}
