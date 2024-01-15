package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.logging.Logger;

import lib.math.Pointi;

public class MoveState extends State {
  public int x;
  public int y;
  public boolean relative;
  public boolean steerMode = false;
  public MoveState(int x, int y, boolean relative) {
    this.x = x;
    this.y = y;
    this.relative = relative;
  }
  public MoveState(Pointi dst) {
    this.x = dst.x;
    this.y = dst.y;
  }
  public MoveState(int x, int y) {
    this.x = x;
    this.y = y;
    this.relative = true;
  }
  public MoveState(int x, int y, boolean relative, boolean steer) {
    this.steerMode = steer;
    this.relative = relative;
    this.x = x;
    this.y = y;
  }
  @Override
  public void init(Chara s) {
    super.init(s);
    // Logger.log("MoveState", "Move");
    // chara.movement.stepDuration = 5f;
    if (relative) {
      if (steerMode || !chara.movement.moveBy(x, y)) {
        chara.movement.lastVelX = x;
        chara.movement.lastVelY = y;
        chara.setState(new IdleState());
      }
      // Logger.log("MoveState", "Steering");
    }
    else {
      chara.bounds.setPosition(x*chara.mapAgent.getWidth(), y*chara.mapAgent.getHeight());
      chara.mapAgent.moveTo(x, y);
    }

  }

  @Override
  public void update(float dt) {
    // chara.anims.current.setAnimDur(0.04f);

  }
  
}
