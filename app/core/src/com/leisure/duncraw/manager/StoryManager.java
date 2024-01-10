package com.leisure.duncraw.manager;

import java.util.LinkedList;
import java.util.Queue;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;

import test.story.scenes.TestScene1;
import test.story.scenes.TestScene2;
import test.story.scenes.TestScene3;

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
    SceneQueue nodes[] = { new TestScene1(), new TestScene2(), new TestScene3() };
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
