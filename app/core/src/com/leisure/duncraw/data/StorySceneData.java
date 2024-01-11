package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.leisure.duncraw.story.SceneQueue;

public class StorySceneData extends Dat {
  public String classname;
  public String title;
  @Override
  public void reset() {
    classname = "None";
    title = "Default title";
  }
  
}
