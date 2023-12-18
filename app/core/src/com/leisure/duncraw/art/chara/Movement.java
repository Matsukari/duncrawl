package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.logging.Logger;

import lib.time.TimePeeker;

public class Movement {
  protected TimePeeker timer = new TimePeeker();
  public float nextStepX = 0;
  public float nextStepY = 0;
  public int velX = 0;
  public int velY = 0;
  public int lastVelX = 0;
  public int lastVelY = 0;
  public boolean moveBy(int x, int y) {
    Logger.log("Movement", String.format("Move to %d %d", x, y));
    velX = x;
    velY = y;
    lastVelX = velX;
    lastVelY = velY;
    return true;
  }
  public void stop() {
    Logger.log("Movement", "Stopped");
    velX = 0;
    velY = 0;
  }
  public void reset() {
    nextStepX = 0;
    nextStepY = 0;
  }
  public static int getLength(int x, int y) { return Math.abs(x) + Math.abs(y); }
  public boolean isMoving(int x, int y) { return velX != 0 || velY != 0; }
  public boolean isMoving() { return isMoving(velX, velY); }
  public boolean update(float dt) { return false; } 
}
