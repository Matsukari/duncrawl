package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.logging.Logger;

public class DashAttackState extends State {
  public Chara target; 
  public DashAttackState(Chara target) {
    this.target = target;
    // Logger.log("DashAttackState", "ASD");
  }
  @Override
public void init(Chara s) {
    super.init(s);

    if (target != null) {
      s.status.nextBonusAttack += s.status.getAttack() / 2;
      // Logger.log("AttackState", "hurt");
      target.setState(new HurtState(chara));
    }
}
  @Override
  public void update(float dt) {

  }
}
