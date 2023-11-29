package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.HurtState;

public class HurtBehaviour extends Observer {
  @Override
  public void invoke(State state) {
    if (state instanceof HurtState) {
      HurtState s = (HurtState)state;
      s.chara.status.health -= s.attacker.status.phyAttack.x;
    }
  }
}
