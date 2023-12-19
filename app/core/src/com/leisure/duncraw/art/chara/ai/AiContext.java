package com.leisure.duncraw.art.chara.ai;

import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.map.Floor;

public class AiContext {
  public final Floor floor;
  public final Player player;
  public AiContext(Floor floor, Player player) {
    this.floor =floor;
    this.player = player;
  }
}
