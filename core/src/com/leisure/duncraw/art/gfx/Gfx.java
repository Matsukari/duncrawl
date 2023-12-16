package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.Art;

public abstract class Gfx extends Art {
  public Gfx(SpriteBatch batch) {
    super(batch);
  } 
  public void update(float dt) {}
  public void stop() {}
  public abstract void start();
  public abstract boolean isFinished();
}
