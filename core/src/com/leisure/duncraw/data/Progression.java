package com.leisure.duncraw.data;

import java.util.ArrayList;

import com.leisure.duncraw.story.Quest;

public class Progression {
  public static class Level {
    public int level;
    public int posX;
    public int poxY;
    public void reset() {
      level = 1;
      posX = 0;
      poxY = 0;
    }
  } 
  public Level level;
  public ArrayList<Quest> mainQuests = new ArrayList<>();
  public ArrayList<Quest> subQuests = new ArrayList<>();
  public Progression() { reset(); }
  public void reset() {
    level = new Level();
    level.reset();
  }
}
