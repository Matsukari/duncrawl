package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.HurtState;

public class HurtBehaviour extends Observer {
  @Override
  public void invoke(State state) {
    if (state instanceof HurtState) {
      HurtState s = (HurtState)state;
      float extraDmg = 0;
      if (s.attacker instanceof Player && ((Player)s.attacker).weapon != null) extraDmg = ((Player)s.attacker).weapon.damage;
      float sustain = s.chara.status.getDefense() - (s.attacker.status.getAttack() + extraDmg);
      if (sustain < 0) s.chara.status.setHealth(s.chara.status.health + (int)sustain);
    }
  }
  @Override
  public Observer copy() {
    return new HurtBehaviour();
  }
}
