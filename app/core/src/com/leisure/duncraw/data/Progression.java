package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.leisure.duncraw.story.Quest;

public class Progression {
  public static class Level {
    public int floor;
    public int scene;
    public int posX;
    public int poxY;
    public void reset() {
      floor = 0;
      scene = 0;
      posX = 0;
      poxY = 0;
    }
  } 
  public Level level;
  public ArrayList<Quest> mainQuests;
  public ArrayList<Quest> subQuests;
  public Progression() { reset(); }
  public void reset() {
    level = new Level();
    level.reset();
    mainQuests = new ArrayList<>();
    subQuests = new ArrayList<>();
  }
}
