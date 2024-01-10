package com.leisure.duncraw.manager;

import java.util.LinkedList;
import java.util.Queue;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;
import com.leisure.duncraw.story.chapters.Chapter1;

public class StoryManager {
  private final GameScreen game;
  public Queue<SceneQueue> scenes;
  public SceneQueue current;
  public int sceneIndex = 0;
  public StoryManager(GameScreen game, int sceneIndex) {
    this.game = game;
    this.sceneIndex = sceneIndex;
    scenes = new LinkedList<SceneQueue>();
    assert sceneIndex >= 0 && sceneIndex < scenes.size();
    SceneQueue nodes[] = { 
      new Chapter1.Scene1(), 
      new Chapter1.Scene2(), 
      new Chapter1.Scene3(), 
      new Chapter1.Scene4(),
      new Chapter1.Scene5(), 
      new Chapter1.Scene6() 

    };
    for (int i = sceneIndex; i < nodes.length; i++) {
      scenes.add(nodes[i]);
    }
  }
  public void play() {
    current = scenes.poll();
    if (current != null) current.play(game);
  }
  public void updateScene() {
    // Finished, then wait for the next scene
    if (current == null) return;
    if (current.update(game) && current.hasEnded()) {
      play();
      sceneIndex++;
    }
  }
}
