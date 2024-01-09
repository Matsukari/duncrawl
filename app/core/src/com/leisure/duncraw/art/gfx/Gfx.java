package com.leisure.duncraw.art.gfx;

import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.Effect;

public abstract class Gfx extends Art implements Effect {
  public boolean loop = false;
  public Gfx() {} 
  @Override public void update(float dt) {}
  @Override public void stop() { loop = false; }
  @Override public abstract void start();
  @Override public abstract boolean isFinished();
}
