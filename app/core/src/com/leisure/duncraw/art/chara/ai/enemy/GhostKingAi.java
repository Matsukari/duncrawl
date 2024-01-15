package com.leisure.duncraw.art.chara.ai.enemy;

import com.leisure.duncraw.art.chara.ai.components.CharaLeafNode;

import behave.execution.ExecutionContext;
import behave.models.DecoratorNode;

public class GhostKingAi extends DecoratorNode.InfiniteRepeaterNode {
  public GhostKingAi() {

  }
  @Override
  public void initialize(ExecutionContext context) {
    super.initialize(context);
  }
  public static class TurnIncorporeal extends CharaLeafNode {
  }
  public static class TurnSolid extends CharaLeafNode {
  }
  public static class SummonWave extends CharaLeafNode {
  }
  
  public static class HurlBall extends CharaLeafNode {
  }
  public static class HurlBallExplode extends CharaLeafNode {
  }


}
