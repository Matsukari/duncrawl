package com.leisure.duncraw.art.chara;

import lib.time.TimePeeker;

public class Movement {
  private TimePeeker timer = new TimePeeker();
  public float nextStepX = 0;
  public float nextStepY = 0;
  public int velX = 0;
  public int velY = 0;
  public int tilePerSec = 1;
  public Movement(int tilePerSec) {
    this.tilePerSec = tilePerSec;
  }
  public void moveBy(int x, int y) {
    velX = x;
    velY = y;
  }
  public boolean update(float dt) {
    nextStepX = 0;
    nextStepY = 0;
    if (timer.sinceLastPeek() > tilePerSec) {
      timer.peek();
      nextStepX = velX;
      nextStepY = velY;
      return true;
    }
    return false;
  } 
}
