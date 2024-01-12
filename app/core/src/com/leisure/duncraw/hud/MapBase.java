package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.Floor;

public class MapBase extends Hud {
  protected Floor lastFloor;
  protected final Player player;
  protected final FloorManager floorManager;
  protected final Label floorLabel;
  public Color floorLabelColor = Color.valueOf("#cccccc");
  public float floorLabelScale = 1f;
  public boolean drawPlayer = true;
  public float minimapScale = 0.016f;
  public Color roomColor = Color.valueOf("#000000");
  public Color playerColor = Color.valueOf("#204020");
  public MapBase(Player player, FloorManager floorManager) {
    this.floorManager = floorManager;
    this.player = player;
  }
  {
    floorLabel = createLabel("Unknown floor");
  }
  protected void init() {
    lastFloor = null;
    floorLabel.setColor(floorLabelColor);
    floorLabel.setScale(floorLabelScale);
    setVisible(true);  

  }
  @Override
  public void update() {
    // Change in floor
    if (lastFloor == null || floorManager.getFloor() != lastFloor) {
      lastFloor = floorManager.getFloor();
      floorLabel.setText(lastFloor.getName());
    }
  }
  @Override
  public void drawShapes() {
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(roomColor);
    drawMap();
    if (drawPlayer) {
      shapeRenderer.setColor(playerColor);
      shapeRenderer.circle(getGlobalX() + player.bounds.x*minimapScale, getGlobalY() + player.bounds.y*minimapScale, 2);
    }
    shapeRenderer.end();
  }
  protected void drawMap() {
    if (lastFloor.generator != null) {
      for (Rectangle room : lastFloor.generator.roomsBuilder.rooms) {
        shapeRenderer.rect(getGlobalX() + room.x*minimapScale, getGlobalY() + room.y*minimapScale, room.width*minimapScale, room.height*minimapScale);
      }
    }
  }
  
  @Override
  public float getGlobalY() {
    return super.getGlobalY() + getHeight()/3;
  }
  @Override
  public float getGlobalX() {
    return super.getGlobalX() + getWidth()/5.5f;
  }
}
