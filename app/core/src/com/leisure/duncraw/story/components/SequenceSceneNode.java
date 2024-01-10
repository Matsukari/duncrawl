package com.leisure.duncraw.story.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneNode;

public class SequenceSceneNode extends SceneNode {
  public Queue<SceneNode> nodes = new LinkedList<>();
  public SceneNode current;
  public SequenceSceneNode(SceneNode... nodes) {
    for (SceneNode node : nodes) this.nodes.add(node);
  }
  @Override
  public void invoke(GameScreen game) {
    current = nodes.poll();
    if (current != null) current.invoke(game);
  }
  @Override
  public boolean update(GameScreen game) {
    if (current != null) { 
      if (current.update(game)) invoke(game);
      return false;
    }
    return true;
  }
  
}
