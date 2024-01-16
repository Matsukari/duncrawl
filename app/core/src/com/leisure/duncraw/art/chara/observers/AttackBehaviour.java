package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.DashAttackState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.MoveState;

import lib.time.Timer;

public class AttackBehaviour extends Observer {
  private final Timer delay = new Timer(500);
  @Override
  public void invoke(State state) {
    if (state instanceof AttackState) {
      chara.lockState = true;
      delay.start();
      if (chara.movement.isMoving()) {
        chara.movement.paused = true;   
      }
    }
    else if (chara.prevState instanceof DashAttackState) {
      chara.lockState = true;
      delay.start();
    }
    else if (chara.prevState instanceof AttackState) {
    }
  }
  @Override
  public void update() {
    if (delay.isFinished()) {
      delay.stop();
      chara.lockState = false;
      chara.movement.paused = false;
      chara.setState(new IdleState());


    }
  }
  @Override
  public Observer copy() {
    return new AttackBehaviour();
  }
}
