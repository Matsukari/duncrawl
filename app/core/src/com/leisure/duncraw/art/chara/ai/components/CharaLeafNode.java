package com.leisure.duncraw.art.chara.ai.components;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;

import behave.execution.ExecutionContext;
import behave.models.LeafNode;
import behave.models.Types.Status;

public class CharaLeafNode extends LeafNode {
  protected Chara chara;
  protected Chara player;
  @Override
  public void initialize(ExecutionContext context) {
    chara = (Chara)context.getVariable("chara");
    player = (Player)context.getVariable("player");
  }
  @Override
  public Status tick(ExecutionContext context) {
    return Status.Failure;
  }  
}
