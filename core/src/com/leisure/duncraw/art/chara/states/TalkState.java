package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class TalkState extends State {
  public Chara target;
  public TalkState(Chara target) { this.target = target; }
  @Override
  public void update(float dt) {

  } 
}
