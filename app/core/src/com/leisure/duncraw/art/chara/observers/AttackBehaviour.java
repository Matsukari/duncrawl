package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.AttackState;

import lib.time.Timer;

public class AttackBehaviour extends Observer {
  private final Timer delay = new Timer(500);
  @Override
  public void invoke(State state) {
    if (state instanceof AttackState) {
      AttackState s = (AttackState)state;
      chara.lockState = true;
      delay.start();
    }
  }
  @Override
  public void update() {
    if (delay.isFinished()) {
      delay.stop();
      chara.lockState = false;


    }
  }
  @Override
  public Observer copy() {
    return new AttackBehaviour();
  }
}
