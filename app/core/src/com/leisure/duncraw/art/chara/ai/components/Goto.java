package com.leisure.duncraw.art.chara.ai.components;

import java.util.Queue;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.ai.Pathfinder;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.map.Floor;

import behave.execution.ExecutionContext;
import behave.models.Types.Status;
import lib.math.Pointi;

public class Goto extends CharaLeafNode {
  private Queue<Pointi> homePath; 
  public int startX, startY;
  public Goto(int x, int y) {
    startX = x;
    startY = y;
  }
  @Override
  public void initialize(ExecutionContext context) {
    super.initialize(context); 
    // startX = (int)context.getVariable("start_position_x");
    // startY = (int)context.getVariable("start_position_y");
    // Floor floor = (Floor)context.getVariable("floor");
    // homePath = new Pathfinder(floor.terrainSet, new Pointi(chara.mapAgent.x, chara.mapAgent.y), new Pointi(startX, startY)).getQueuedPath();
    // Pointi next = homePath.removeFirst();
    // chara.setState(new MoveState(next.x-chara.mapAgent.x, next.y-chara.mapAgent.y));

    Pointi step = Pathfinder.getImmediateDiagonal(new Pointi(chara.mapAgent.x, chara.mapAgent.y), new Pointi(startX, startY));
    chara.setState(new MoveState(step.x, step.y));
    // context.setVariable("home_path", homePath);
  }
  public int distance() {
    return (Math.abs(chara.mapAgent.x - startX) + Math.abs(chara.mapAgent.y - startY));
  }
  @Override
  public Status tick(ExecutionContext context) {
    if (distance() <= 2) return Status.Success;
    else if (!chara.movement.isMoving()) {
      // Pointi next = homePath.removeFirst();
      // if (next != null) chara.setState(new MoveState(next.x, next.y));
      // else return Status.Success;
      Pointi step = Pathfinder.getImmediateDiagonal(new Pointi(chara.mapAgent.x, chara.mapAgent.y), new Pointi(startX, startY));
      chara.setState(new MoveState(step.x, step.y));

    }
    // if (chara.mapAgent.distance(player.mapAgent) == 1) return Status.Success;
    return Status.Running;
  }
} 
