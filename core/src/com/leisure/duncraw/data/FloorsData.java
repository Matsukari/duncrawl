package com.leisure.duncraw.data;

import java.util.ArrayList;

public class FloorsData {
  public String bossRoom1;
  public String bossRoom2;
  public String bossRoom3;
  public String safePlace1;
  public String safePlace2;
  public String startingHall;
  public ArrayList<String> floorsDat;
  public void reset() {
    bossRoom3 = "floors/bossroom_final.tmx";
    bossRoom2 = "floors/bossroom_middle.tmx";
    bossRoom1 = "floors/bossroom_start.tmx";
    safePlace1 = "floors/safeplace_start.tmx";
    safePlace2 = "floors/safeplace_middle.tmx";
    startingHall = "floors/starting_room.tmx";
    floorsDat = new ArrayList<>();
    for (int i = 0; i < 10; i++)  {
      floorsDat.add(String.format("floors/random/%d", i));
    }
  }
}
