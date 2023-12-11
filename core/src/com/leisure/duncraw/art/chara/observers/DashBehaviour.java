package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.DashState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.logging.Logger;

public class DashBehaviour extends Observer {
  @Override
  public void invoke(State state) {
    if (state instanceof DashState) {
      if (chara.movement.isMoving()) chara.movement.moveBy(chara.movement.lastVelX, chara.movement.lastVelY);
      else chara.moveBy(chara.movement.lastVelX, chara.movement.lastVelY);
      chara.movement.stepDuration = 10f;
      Logger.log("DashBehaviour", String.format("Dash: %d %d", chara.movement.lastVelX, chara.movement.lastVelY));
    }
  } 
  @Override
  public Observer copy() {
    return new DashBehaviour();
  }
}
