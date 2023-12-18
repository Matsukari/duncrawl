package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class MoveState extends State {
  public int x;
  public int y;
  public boolean relative;
  public MoveState(int x, int y, boolean relative) {
    this.x = x;
    this.y = y;
    this.relative = relative;
  }
  public MoveState(int x, int y) {
    this.x = x;
    this.y = y;
    this.relative = true;
  }
  @Override
  public void init(Chara s) {
    super.init(s);
    chara.movement.stepDuration = 4f;
    if (relative) {
      chara.anims.current.face(x, y);
      if (!chara.movement.moveBy(x, y)) chara.setState(new IdleState());
    }
    else {
      chara.bounds.setPosition(x*chara.mapAgent.getWidth(), y*chara.mapAgent.getHeight());
      chara.mapAgent.moveTo(x, y);
    }

  }
  @Override
  public void update(float dt) {}
  
}
