package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.logging.Logger;

public class HurtBehaviour extends Observer {
  @Override
  public void invoke(State state) {
    if (state instanceof HurtState) {
      Logger.log("HurtBehaviour", "Hurt! ");
      HurtState s = (HurtState)state;

      chara.status.hurt(s.attacker);
    }
  }
  @Override
  public Observer copy() {
    return new HurtBehaviour();
  }
}
