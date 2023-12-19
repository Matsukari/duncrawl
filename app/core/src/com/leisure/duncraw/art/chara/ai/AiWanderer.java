package com.leisure.duncraw.art.chara.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Queue;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.ai.components.CharaLeafNode;
import com.leisure.duncraw.art.chara.ai.components.PlayerNodes;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.map.Floor;

import behave.execution.ExecutionContext;
import behave.models.DecoratorNode;
import behave.models.CompositeNode.SequenceNode;
import behave.models.Types.Status;
import lib.math.Pointi;

public class AiWanderer extends DecoratorNode.InfiniteRepeaterNode {
  public final Floor floor;
  public final Player player;
  public AiWanderer(Floor floor, Player player) {
    this.floor =floor;
    this.player = player;
  }
  @Override
  public void initialize(ExecutionContext context) {
    Chara chara = (Chara)context.getVariable("chara");
    context.setVariable("start_position_x", chara.mapAgent.x);
    context.setVariable("start_position_y", chara.mapAgent.y);
    context.setVariable("player", player);
    context.setVariable("floor", floor);
    SequenceNode wander = new SequenceNode();
    SequenceNode spottedPlayer = new SequenceNode();
    // wander.addChild(new GoBack());
    wander.addChild(new WanderAround());
    wander.addChild(new PlayerNodes.Near(7));
    spottedPlayer.addChild(new PlayerNodes.Near(7));
    spottedPlayer.addChild(new PlayerNodes.Goto());
    spottedPlayer.addChild(new PlayerNodes.Near(1));
    spottedPlayer.addChild(new PlayerNodes.Attack());
    spottedPlayer.addChild(new PlayerNodes.Dead());
    wander.addChild(spottedPlayer);
    wander.addChild(new GoBack());
    addChild(wander);
    wander.initialize(context);
  }
  public static class WanderAround extends CharaLeafNode {
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
  public static class GoBack extends CharaLeafNode {
    private Queue<Pointi> homePath;
    int startX, startY;
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context); 
      startX = (int)context.getVariable("start_position_x");
      startY = (int)context.getVariable("start_position_y");
      Floor floor = (Floor)context.getVariable("floor");
      // homePath = new Pathfinder(floor.terrainSet, new Pointi(chara.mapAgent.x, chara.mapAgent.y), new Pointi(startX, startY)).getQueuedPath();
      // Pointi next = homePath.removeFirst();
      // chara.setState(new MoveState(next.x-chara.mapAgent.x, next.y-chara.mapAgent.y));

      Pointi step = Pathfinder.getImmediateDiagonal(new Pointi(chara.mapAgent.x, chara.mapAgent.y), new Pointi(startX, startY));
      chara.setState(new MoveState(step.x, step.y));
      context.setVariable("home_path", homePath);
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
}
