package com.leisure.duncraw.art.chara.observers.dark;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.dark.ShadowBindSkill;

public class ShadowBindBehaviour extends Observer {
  private ShadowBindSkill s;
  @Override
  public void invoke(State state) {
    if (state instanceof ShadowBindSkill) {
      s = (ShadowBindSkill)state;
      // s.target.status.health -= s.chara.status.phyAttack.x;
    }
  }
  @Override
  public void update() {
    
  }
  @Override
  public Observer copy() {
    return new ShadowBindBehaviour();
  }
}
