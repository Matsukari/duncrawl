package com.leisure.duncraw.art.gfx;

import com.leisure.duncraw.art.Art;

public abstract class Gfx extends Art {
  public boolean loop = false;
  public Gfx() {} 
  public void update(float dt) {}
  public void stop() { loop = false; }
  public abstract void start();
  public abstract boolean isFinished();
}
