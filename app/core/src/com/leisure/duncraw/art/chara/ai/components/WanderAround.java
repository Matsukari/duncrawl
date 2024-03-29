package com.leisure.duncraw.art.chara.ai.components;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.states.MoveState;

import behave.execution.ExecutionContext;
import behave.models.Types.Status;
import lib.time.Timer;

public class WanderAround extends CharaLeafNode {
  private Timer timer = new Timer(1500);
  @Override
  public void initialize(ExecutionContext context) {  
    super.initialize(context);
    timer.start();
  }
  @Override
  public Status tick(ExecutionContext context) {
    if (timer.isFinished()) {
      timer.stop();
      int step[] = { MathUtils.random(-1, 1), MathUtils.random(-1, 1) };
      if (step[0] != 0 && step[1] != 0) step[MathUtils.random(1)] = 0;
      chara.setState(new MoveState(step[0], step[1]));

    }
    else if (chara.movement.isMoving() || timer.isTicking()) return Status.Running;
    return Status.Success;
  }
} 
