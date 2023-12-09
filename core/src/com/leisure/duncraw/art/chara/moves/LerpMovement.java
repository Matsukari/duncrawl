package com.leisure.duncraw.art.chara.moves;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Movement;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;

// normalized lerping
public class LerpMovement extends Movement {
  private float stepTaken = 0;
  public float stepDuration = 0;
  private float time = 0f;
  private final Chara chara;
  public LerpMovement(Chara chara, float stepDuration) {
    this.stepDuration = stepDuration;
    this.chara = chara;
  }
  @Override
  public boolean moveBy(int x, int y) {
    lastVelY = y;
    lastVelX = x;
    Terrain terrain = chara.mapAgent.getTerrainBy(x, y);
    if (terrain != null && terrain.traversable()) {
      super.moveBy(x, y);
      return true;
    }
    else if (terrain != null) {
      Logger.log("LerpMovement", "cannot move: terrain is " + Boolean.toString(terrain.traversable()));
    }
    else
    Logger.log("LerpMovement", "terrain you are in is null;");
    return false;
  }
  @Override
  public void reset() {
    super.reset();
    stepTaken = 0;
    time = 0;
  }
  @Override
  public boolean update(float dt) {
    boolean ahead = stepTaken > Math.abs(velX + velY);
    if (ahead || (time >= stepDuration && ahead)) {
      // Logger.log("LerpMovement", "step taken: " + Float.toString(stepTaken));
      reset();
      stop();
      return true;
    }
    dt *= stepDuration;
    time += dt;
    nextStepX = velX * dt;
    nextStepY = velY * dt;
    stepTaken += Math.abs(nextStepX + nextStepY);
    if (stepTaken > Math.abs(velX + velY)) {
      float excessX = stepTaken - Math.abs(velX);
      float excessY = stepTaken - Math.abs(velY);
      if (velX != 0) nextStepX -= excessX * velX;
      else if (velY != 0) nextStepY -= excessY * velY; 
      stepTaken = 1.0000001f;
      // Logger.log("LerpMovement", "excess: " + Float.toString(excessX));
    }
    chara.bounds.x += chara.movement.nextStepX * chara.mapAgent.getWidth();
    chara.bounds.y += chara.movement.nextStepY * chara.mapAgent.getHeight();
    return false;
  }

}
