package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class DashAttackState extends State {
  public Chara target; 
  public DashAttackState(Chara target) {
    this.target = target;
  }
  @Override
  public void update(float dt) {

  }
}
