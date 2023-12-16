package com.leisure.duncraw.art.chara.observers.dark;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.dark.ShadowCloakSkill;

public class ShadowCloakBehaviour extends Observer {
  private ShadowCloakSkill s;
  @Override
  public void invoke(State state) {
    if (state instanceof ShadowCloakSkill) {
      s = (ShadowCloakSkill)state;
      // s.target.status.health -= s.chara.status.phyAttack.x;
    }
  }
  @Override
  public void update() {
    
  }
  @Override
  public Observer copy() {
    return new ShadowCloakBehaviour();
  }
  
}
