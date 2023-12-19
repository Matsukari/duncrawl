package com.leisure.duncraw.art.chara.ai;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.ai.components.CharaLeafNode;
import com.leisure.duncraw.art.chara.ai.components.PlayerNodes;
import com.leisure.duncraw.art.chara.states.MoveState;

import behave.execution.ExecutionContext;
import behave.models.DecoratorNode;
import behave.models.CompositeNode.SequenceNode;
import behave.models.Types.Status;

public class AiWanderer extends DecoratorNode.InfiniteRepeaterNode {
  @Override
  public void initialize(ExecutionContext context) {
    Chara chara = (Chara)context.getVariable("chara");
    context.setVariable("start_position_x", chara.mapAgent.x);
    context.setVariable("start_position_y", chara.mapAgent.y);
    SequenceNode wander = new SequenceNode();
    SequenceNode spottedPlayer = new SequenceNode();
    wander.addChild(new WanderAround());
    wander.addChild(new PlayerNodes.Near(6));
    spottedPlayer.addChild(new PlayerNodes.Near(6));
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
      chara.setState(new MoveState(MathUtils.random(-1, 1), MathUtils.random(-1, 1)));
    }
    @Override
    public Status tick(ExecutionContext context) {
      if (chara.movement.isMoving()) return Status.Running;
      return Status.Success;
    }
  } 
  public static class GoBack extends CharaLeafNode {
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context); 
      int startX = (int)context.getVariable("start_position_x");
      int startY = (int)context.getVariable("start_position_y");
      chara.setState(new MoveState(startX, startY, false));
    }
    @Override
    public Status tick(ExecutionContext context) {
      if (chara.mapAgent.distance(player.mapAgent) == 1) return Status.Success;
      return Status.Running;
    }
  } 
}
