package com.leisure.duncraw.data;

public class FloorData {
  public String bossRoom1;
  public String bossRoom2;
  public String bossRoom3;
  public String safePlace1;
  public String safePlace2;
  public String startingHall;
  public void reset() {
    bossRoom3 = "bossroom_final";
    bossRoom2 = "bossroom_middle";
    bossRoom1 = "bossroom_start";
    safePlace1 = "safeplace_start";
    safePlace2 = "safeplace_middle";
    startingHall = "starting_hall";
  }
}
