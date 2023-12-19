package com.leisure.duncraw.art.chara.ai.components;

import com.leisure.duncraw.art.chara.states.MoveState;

import behave.execution.ExecutionContext;
import behave.models.Types.Status;
import lib.time.TimePeeker;

public class PlayerNodes {
  public static class Near extends CharaLeafNode {
    public Near(int area) {}
    @Override
    public Status tick(ExecutionContext context) {
      if (Math.abs(chara.mapAgent.x - player.mapAgent.x) <= 5 || Math.abs(chara.mapAgent.y - player.mapAgent.y) <= 5) 
        return Status.Success;
      return Status.Failure;
    }
  }
  public static class Attack extends CharaLeafNode {
    private TimePeeker timer = new TimePeeker();
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context);
      timer.peek();
    }
    @Override
    public Status tick(ExecutionContext context) {
      if (timer.sinceLastPeek() >= 2000) return Status.Success;
      return Status.Running;
    }
  } 

  public static class Goto extends CharaLeafNode {
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context);
      chara.setState(new MoveState(1, 1));
    }
    @Override
    public Status tick(ExecutionContext context) {
      if (chara.movement.isMoving()) return Status.Running;
      return Status.Success;
    }
  }
  public static class Dead extends CharaLeafNode {
    @Override
    public Status tick(ExecutionContext context) {
      if (player.status.dead) return Status.Success;
      return Status.Failure;
    }
  }
}
