package com.leisure.duncraw.story.components;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneNode;

public class BlockCameraSceneNode extends SceneNode {
  public boolean blockCamera;
  public BlockCameraSceneNode(boolean blockCamera) {
    this.blockCamera = blockCamera;
  }
  @Override
  public void invoke(GameScreen game) {
    game.blockCamera = blockCamera;
  } 
  @Override
  public boolean update(GameScreen game) {
    return true;
  }
}
