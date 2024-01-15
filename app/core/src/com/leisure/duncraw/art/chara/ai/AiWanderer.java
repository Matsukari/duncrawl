package com.leisure.duncraw.art.chara.ai;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.ai.components.Goto;
import com.leisure.duncraw.art.chara.ai.components.PlayerNodes;
import com.leisure.duncraw.art.chara.ai.components.WanderAround;

import behave.execution.ExecutionContext;
import behave.models.DecoratorNode;
import behave.models.CompositeNode.SequenceNode;

public class AiWanderer extends DecoratorNode.InfiniteRepeaterNode {
  @Override
  public void initialize(ExecutionContext context) {
    Chara chara = (Chara)context.getVariable("chara");
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
    wander.addChild(new Goto(chara.mapAgent.x, chara.mapAgent.y));
    addChild(wander);
    wander.initialize(context); 
  }
}
