package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leisure.duncraw.art.chara.Player;

public class StatusFull extends StatusHud {
  public StatusFull(Player player) {
    this.player = player;
    // add(rankLabel).left().top().padTop(20);
    setVisible(true);  
  }
  @Override
  public void drawShapes() {
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(healthColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY()+getHeight(), player.status.health * 1.3f, 5);
    shapeRenderer.setColor(staminaColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY()+getHeight()-8, player.status.stamina * 1.3f, 5);
    shapeRenderer.end();
  }
}
