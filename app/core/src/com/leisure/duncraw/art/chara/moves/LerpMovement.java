package com.leisure.duncraw.art.chara.moves;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Movement;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;

// normalized lerping
public class LerpMovement extends Movement {
  private final Chara chara;
  private float time = 0f;
  private float stepTaken = 0;
  public boolean paused= false;
  public float stepDuration = 0;
  public int length = 1;
  public LerpMovement(Chara chara, float stepDuration) {
    this.stepDuration = stepDuration;
    this.chara = chara;
  }
  @Override
  public boolean moveBy(int x, int y) {
    length = getLength(x, y);
    x = MathUtils.clamp(x, -1, 1);
    y = MathUtils.clamp(y, -1, 1);
    lastVelX = x;
    lastVelY = y;
    if (chara.mapAgent.map.canTravel(chara.mapAgent.x + x, chara.mapAgent.y + y)) {
      // Logger.log("Position of player", String.format("%d %d", chara.mapAgent.x, chara.mapAgent.y)); 
      super.moveBy(x, y);
      return true;
    } 
    length = 0;
    reset();
    stop();
    return false;
  }
  @Override
  public void reset() {
    super.reset();
    stepTaken = 0;
    time = 0;
  }
  public void pause() {
    paused = true;
  }
  @Override
  public boolean update(float dt) {
    if (paused) return false;
    float direction = Math.abs(velX) + Math.abs(velY); 
    boolean ahead = stepTaken > direction;
    if (ahead || (time >= stepDuration && ahead)) {
      // chara.bounds.setPosition(MathUtils.ceil(chara.bounds.x), MathUtils.ceil(chara.bounds.y));
      chara.mapAgent.moveBy(lastVelX, lastVelY);
      reset();
      if (length <= 1) {
        // Logger.log("LerpMovement", "Stopped moving");
        stop();
        return true;
      } else {
        if (lastVelX != 0) moveBy(lastVelX * (length - 1), 0);
        else if (lastVelY != 0) moveBy(0, lastVelY * (length - 1));
      }
      Logger.log("LerpMovement", String.format("Moved by %d %s", lastVelX, lastVelY));
      return false;
    }
    dt *= stepDuration;
    time += dt;
    nextStepX = velX * dt;
    nextStepY = velY * dt;
    stepTaken += Math.abs(nextStepX) + Math.abs(nextStepY);
    if (stepTaken > direction) {
      float excessX = stepTaken - Math.abs(velX);
      float excessY = stepTaken - Math.abs(velY);
      if (velX != 0) nextStepX -= excessX * velX;
      else if (velY != 0) nextStepY -= excessY * velY; 
      // stepTaken = direction + 0.0000001f;
      // Logger.log("LerpMovement", "step taken: " + Float.toString(stepTaken));
    }
    chara.bounds.x += chara.movement.nextStepX * chara.mapAgent.getWidth();
    chara.bounds.y += chara.movement.nextStepY * chara.mapAgent.getHeight();
    // chara.mapAgent.moveTo((int)chara.bounds.x/chara.mapAgent.getWidth(), chara.mapAgent.map.terrainSet.rows - ((int)chara.bounds.y/chara.mapAgent.getHeight()));
    return false;
  }
  

}
