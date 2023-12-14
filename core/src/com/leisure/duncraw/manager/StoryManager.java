package com.leisure.duncraw.manager;

import java.util.LinkedList;
import java.util.Queue;

import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;

import test.story.scenes.TestScene1;
import test.story.scenes.TestScene2;

public class StoryManager {
  private final GameScreen game;
  public Queue<SceneQueue> stories;
  public SceneQueue current;
  public StoryManager(GameScreen game, int sceneIndex) {
    this.game = game;
    stories = new LinkedList<SceneQueue>();
    SceneQueue storyNodes[] = { new TestScene1(), new TestScene2() };
    for (int i = sceneIndex; i < storyNodes.length; i++) {
      stories.add(storyNodes[i]);
    }
  }
  public void play() {
    current = stories.poll();
    if (current != null) current.play(game);
  }
  public void updateScene() {
    // Finished, then wait for the next scene
    if (current == null) return;
    if (current.update(game) && current.hasEnded()) play();
  }
}
