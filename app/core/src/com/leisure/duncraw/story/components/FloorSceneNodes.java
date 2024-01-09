package com.leisure.duncraw.story.components;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneNode;

public class FloorSceneNodes {
  public static class ChangeFloor extends SceneNode {
    private int level;
    public ChangeFloor(int level) {
      this.level = level;
      
    }
    @Override
    public void invoke(GameScreen game) {
      game.floorManager.loadFloor(level);
      game.floorManager.stageFloor(game.player, game.charaManager, game.context);
      game.floorManager.getFloor().lightEnvironment.update();
      game.floorManager.getFloor().lightEnvironment.cast(game.camera);
    }
    @Override
    public boolean update(GameScreen game) {
      return true;
    }
  }
  
}
