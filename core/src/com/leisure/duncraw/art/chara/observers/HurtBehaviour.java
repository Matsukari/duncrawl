package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.HurtState;

public class HurtBehaviour extends Observer {
  @Override
  public void invoke(State state) {
    if (state instanceof HurtState) {
      HurtState s = (HurtState)state;
      float sustain = s.chara.status.getDefense() - s.attacker.status.getAttack();
      if (sustain < 0) s.chara.status.health += sustain;
    }
  }
  @Override
  public Observer copy() {
    return new HurtBehaviour();
  }
}
