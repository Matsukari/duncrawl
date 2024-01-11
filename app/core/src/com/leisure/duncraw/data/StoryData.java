package com.leisure.duncraw.data;

import java.util.ArrayList;
import java.util.Calendar;

import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.story.SceneQueue;

public class StoryData extends Dat {
  public ArrayList<StorySceneData> scenes; 
  @Override
  public void reset() {
    scenes = new ArrayList<>();
  }
  public ArrayList<SceneQueue> getScenes() {
    ArrayList<SceneQueue> nodes = new ArrayList<>();
    try {
      for (StorySceneData scene : scenes) {
        nodes.add((SceneQueue)Class.forName("com.leisure.duncraw.story.chapters." + scene.classname).getDeclaredConstructor().newInstance());
      }
    } catch (Exception e) { Logger.error(e); }
    return nodes;
  }
  
}
