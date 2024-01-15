package com.leisure.duncraw.art.chara.ai.components;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.states.MoveState;

import behave.execution.ExecutionContext;
import behave.models.Types.Status;

public class WanderAround extends CharaLeafNode {
  @Override
  public void initialize(ExecutionContext context) {  
    super.initialize(context);
    int step[] = { MathUtils.random(-1, 1), MathUtils.random(-1, 1) };
    if (step[0] != 0 && step[1] != 0) step[MathUtils.random(1)] = 0;
    chara.setState(new MoveState(step[0], step[1]));
  }
  @Override
  public Status tick(ExecutionContext context) {
    if (chara.movement.isMoving()) return Status.Running;
    return Status.Success;
  }
} 
