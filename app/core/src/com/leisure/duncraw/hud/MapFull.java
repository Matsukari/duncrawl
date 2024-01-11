package com.leisure.duncraw.hud;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.manager.FloorManager;

public class MapFull extends MapBase {
  private final Label unavLabel;
  public MapFull(Player player, FloorManager floorManager) {
    super(player, floorManager);
    unavLabel = createLabel("Map for this floor not available");
    minimapScale = 0.1f;
    add(unavLabel).center().expand();
    row();
    add(floorLabel).right().bottom().expand().padTop(100);
  }
  @Override 
  protected void drawMap() {
    if (lastFloor.generator != null && lastFloor.generator.data.generation != null) {
      unavLabel.setVisible(false);
      for (Rectangle room : lastFloor.generator.roomsBuilder.rooms) {
        shapeRenderer.rect(getGlobalX() + room.x*minimapScale, getGlobalY() + room.y*minimapScale, room.width*minimapScale, room.height*minimapScale);
      }
    }
    else 
      unavLabel.setVisible(true);
  }
}
