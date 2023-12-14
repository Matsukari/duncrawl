package com.leisure.duncraw.story;

import com.leisure.duncraw.screen.GameScreen;

public class SceneNode {
  public boolean isFinished = false;
  public SceneNode() {}
  public void invoke(GameScreen game) {}
  public boolean update(GameScreen game) { return false; }
  public boolean nextCondition(GameScreen game) { return false; }
}
