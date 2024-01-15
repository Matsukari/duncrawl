package com.leisure.duncraw.art.chara.ai.components;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.screen.GameScreen.Context;

import behave.execution.ExecutionContext;
import behave.models.LeafNode;
import behave.models.Types.Status;

public class CharaLeafNode extends LeafNode {
  protected Chara chara;
  protected Player player;
  @Override
  public void initialize(ExecutionContext context) {
    chara = (Chara)context.getVariable("chara");
    player = (Player)context.getVariable("player");
  }
  public Floor getFloor(ExecutionContext context) {
    return (Floor)context.getVariable("floor");
  }
  public Context getContext(ExecutionContext context) {
    return (Context)context.getVariable("context");
  }
  @Override
  public Status tick(ExecutionContext context) {
    return Status.Failure;
  }  
}
