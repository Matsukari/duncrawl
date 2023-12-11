package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class MoveState extends State {
  @Override
  public void init(Chara s) {
    super.init(s);
    chara.movement.stepDuration = 4f;
  }
  @Override
  public void update(float dt) {}
  
}
