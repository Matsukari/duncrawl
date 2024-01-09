package com.leisure.duncraw.story;

import java.util.LinkedList;
import java.util.Queue;

import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;

public class SceneQueue {
  public Queue<SceneNode> scenes = new LinkedList<>();
  public SceneNode current;
  // public int index = 0;
  public boolean started = false;
  public boolean play(GameScreen game) {
    started = true;
    return next(game);
  }
  public boolean next(GameScreen game) {
    Logger.log("SceneQueue", "Next scene");
    current = scenes.poll();
    if (current != null) {
      current.invoke(game);
      // index++;
    } 
    else {
      return true;
    }
    return false; 
  }
  public boolean update(GameScreen game) {
    if (started && current == null) return true;
    if (current.update(game)) return next(game);
    return false;
  }
  public boolean hasEnded() {
    return started && current == null; 
  }
  public boolean nextCondition(GameScreen game) { 
    return false; 
  }
}
