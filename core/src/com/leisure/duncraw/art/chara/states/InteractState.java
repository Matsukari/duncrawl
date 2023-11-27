package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class InteractState extends State {
  public Chara target;
  public InteractState(Chara target) { this.target = target; }
  @Override
  public void update(float dt) {

  }
  
}
