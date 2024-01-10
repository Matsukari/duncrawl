package com.leisure.duncraw.story.components;

import java.util.ArrayList;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneNode;

public class ParallelSceneNode extends SceneNode {
  public ArrayList<SceneNode> nodes = new ArrayList<>();
  public ParallelSceneNode(SceneNode... nodes) {
    for (SceneNode node : nodes) this.nodes.add(node);
  }
  @Override
  public void invoke(GameScreen game) {
    for (SceneNode node : nodes) node.invoke(game);
  }
  @Override
  public boolean update(GameScreen game) {
    boolean combinedResult = true;
    for (SceneNode node : nodes) {
      combinedResult = combinedResult && node.update(game);
    }
    return combinedResult;
  }
  
}
