package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.Floor;

public class MapHud extends Hud {
  private Floor lastFloor;
  private final Player player;
  private final FloorManager floorManager;
  private final Label floorLabel;
  public float minimapScale = 0.016f;
  public MapHud(Player player, FloorManager floorManager) {
    this.floorManager = floorManager;
    this.player = player;
    floorLabel = createLabel("Unknown floor");
    lastFloor = null;
    add(floorLabel).right().top().expand().padTop(100);
    setVisible(true);  
  }
  @Override
  public void update() {
    // Change in floor
    if (lastFloor == null || floorManager.getCurrentFloor() != lastFloor) {
      lastFloor = floorManager.getCurrentFloor();
      floorLabel.setText(lastFloor.getName());
    }
  }
  @Override
  public void drawShapes() {
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(Color.GRAY);
    if (lastFloor.generator != null)
      for (Rectangle room : lastFloor.generator.roomsBuilder.rooms) {
        shapeRenderer.rect(getGlobalX() + room.x*minimapScale, getGlobalY() + room.y*minimapScale, room.width*minimapScale, room.height*minimapScale);
    }
    shapeRenderer.setColor(Color.GREEN);
    shapeRenderer.circle(getGlobalX() + player.bounds.x*minimapScale, getGlobalY() + player.bounds.y*minimapScale, 2);
    shapeRenderer.end();
  }

}
