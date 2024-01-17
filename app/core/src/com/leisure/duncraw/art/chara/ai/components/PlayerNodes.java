package com.leisure.duncraw.art.chara.ai.components;

import com.badlogic.gdx.utils.Queue;
import com.leisure.duncraw.art.chara.ai.Pathfinder;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.logging.Logger;

import behave.execution.ExecutionContext;
import behave.models.Types.Status;
import lib.math.Pointi;
import lib.time.TimePeeker;

public class PlayerNodes {
  public static class Near extends CharaLeafNode {
    public int area;
    public Near(int area) { 
      this.area = area;
    }
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context);
    }
    @Override
    public Status tick(ExecutionContext context) {
      if (chara.mapAgent.distance(player.mapAgent) <= area) {
        // Logger.log("PlayerNodes", "Near! ");
        return Status.Success;
      }
      // Logger.log("PlayerNodes", String.format("Too far: "));
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
      if (timer.sinceLastPeek() >= 500) {
        player.setState(new HurtState(chara));
        // player.status.setHealth(player.status.health - 20);
        return Status.Success;
      }
      return Status.Running;
    }
  } 

  public static class Goto extends CharaLeafNode {
    private Queue<Pointi> path;
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context);
      // path = new Pathfinder(getFloor(context).terrainSet, new Pointi(chara.mapAgent.x, chara.mapAgent.y), 
      //     new Pointi(player.mapAgent.x, player.mapAgent.y)).getQueuedPath();
      // Pointi next = path.removeFirst();
      if (chara.movement.isMoving()) return;
      Pointi step = Pathfinder.getImmediateDiagonal(new Pointi(chara.mapAgent.x, chara.mapAgent.y), new Pointi(player.mapAgent.x, player.mapAgent.y));
      chara.setState(new MoveState(-step.x, -step.y));
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
