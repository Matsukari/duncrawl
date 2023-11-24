package com.leisure.duncraw.data;

public class CharasData {
  public String player_idle;
  public String player_icon;
  public String player_move;
  public String ghost_idle;
  public void reset() {
    player_idle = "player_idle.png";
    player_icon = "player_icon.png";
    player_move = "player_move.png";
    ghost_idle = "ghost_idle.png";
  }
}
