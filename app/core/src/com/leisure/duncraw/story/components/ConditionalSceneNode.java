package com.leisure.duncraw.story.components;

import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneNode;

public class ConditionalSceneNode extends SceneNode {
  @FunctionalInterface
  public static interface Conditional {
    public boolean condition();

  }
  public Conditional conditional;
  public ConditionalSceneNode(Conditional function) {
    this.conditional = function;
  }
  @Override
  public void invoke(GameScreen game) {
  }  
  @Override
  public boolean update(GameScreen game) {
    return conditional.condition();
  }
}
