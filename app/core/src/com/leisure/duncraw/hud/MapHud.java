package com.leisure.duncraw.hud;

import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.manager.FloorManager;

public class MapHud extends MapBase {
  public MapHud(Player player, FloorManager floorManager) {
    super(player, floorManager);
    add(floorLabel).right().top().expand().padTop(100);
  }
}
