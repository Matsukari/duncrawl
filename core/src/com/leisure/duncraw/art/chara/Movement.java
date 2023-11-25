package com.leisure.duncraw.art.chara;

import lib.time.TimePeeker;

public class Movement {
  protected TimePeeker timer = new TimePeeker();
  public float nextStepX = 0;
  public float nextStepY = 0;
  public int velX = 0;
  public int velY = 0;
  public void moveBy(int x, int y) {
    velX = x;
    velY = y;
  }
  public void stop() {
    velX = 0;
    velY = 0;
  }
  public boolean isMoving() { return velX != 0 || velY != 0; }
  public void apply(Chara chara) {}
  public boolean update(float dt) { return false; } 
}
