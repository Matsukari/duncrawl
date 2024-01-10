package com.leisure.duncraw.data;

public class CharasData {
  public String player;
  public String ghost;
  public String bard;
  public String ghostKing;
  public void reset() {
    player = "dat/chara/player.dat";
    ghost = "dat/chara/ghost.dat";
    bard = "dat/chara/bard.dat";
    ghostKing = "dat/chara/mons/ghost_king.dat";
  }
}
