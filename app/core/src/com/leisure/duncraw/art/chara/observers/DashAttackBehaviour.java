package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.DashAttackState;
import com.leisure.duncraw.art.chara.states.DashState;

public class DashAttackBehaviour extends Observer {

  @Override
  public void invoke(State state) {
    if (state instanceof DashAttackState) {
    
    }
  }
  @Override
  public void update() {
  }
  @Override
  public Observer copy() {
    return new DashAttackBehaviour();
  } 
}
