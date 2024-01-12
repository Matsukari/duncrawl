package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.manager.FloorManager;

public class MapHud extends MapBase {
  public float floorLabelDistance = 10f;
  public MapHud(Player player, FloorManager floorManager) {
    super(player, floorManager);
    roomColor = Color.DARK_GRAY;
    init(); 
    add(floorLabel).right().bottom().expand().padTop(floorLabelDistance);
    floorLabel.setScale(0.7f);
  }
  @Override
  public float getGlobalY() {
    return super.getGlobalY() - 10;
  }
}
