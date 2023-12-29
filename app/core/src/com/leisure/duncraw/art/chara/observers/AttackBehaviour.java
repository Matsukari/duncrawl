package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.AttackState;

public class AttackBehaviour extends Observer {
  @Override
  public void invoke(State state) {
    if (state instanceof AttackState) {
      AttackState s = (AttackState)state;
    }
  }
  @Override
  public Observer copy() {
    return new AttackBehaviour();
  }
}
