package com.leisure.duncraw.art.chara.moves;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Movement;
import com.leisure.duncraw.logging.Logger;

import lib.time.TimePeeker;

// normalized lerping
public class LerpMovement extends Movement {
  private float stepTaken = 0;
  private float stepDuration = 0;
  private float time = 0f;
  public LerpMovement(float stepDuration) {
    this.stepDuration = stepDuration;
  }
  @Override
  public void moveBy(int x, int y) {
    super.moveBy(x, y);
  }
  @Override
  public boolean update(float dt) {
    time += dt;
    nextStepX = velX * dt;
    nextStepY = velY * dt;
    stepTaken += Math.abs(nextStepX + nextStepY);
    if (time >= stepDuration && stepTaken >=Math.abs(velX + velY)) {
      Logger.log("LerpMovement", "step taken: " + Float.toString(stepTaken));
      nextStepY = 0;
      nextStepX = 0;
      stepTaken = 0;
      time = 0;
      stop();
      return true;
    }
    return false;
  }
  @Override
  public void apply(Chara chara) {
    chara.bounds.x += chara.movement.nextStepX * chara.mapAgent.getWidth();
    chara.bounds.y += chara.movement.nextStepY * chara.mapAgent.getHeight();
  }
}
