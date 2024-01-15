package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class IdleState extends State {
  @Override
  public void init(Chara s) {
    super.init(s);
  }
  @Override
  public void update(float dt) {
    // chara.anims.current.setAnimDur(0.1f);
  }
}
