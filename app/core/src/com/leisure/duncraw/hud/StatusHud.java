package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.art.chara.Player;

public class StatusHud extends Hud {
  protected final Label rankLabel;
  protected Player player;
  public StatusHud(Player player) {
    this.player = player;
    rankLabel = createLabel("Beginner");
    add(rankLabel).left().top().padTop(20);
    setVisible(true);  
  }
  @Override
  public void drawShapes() {
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(Color.RED);
    shapeRenderer.rect(getX(), getY()+30, player.status.health, 5);
    shapeRenderer.setColor(Color.YELLOW);
    shapeRenderer.rect(getX(), getY()+25, player.status.stamina, 5);
    shapeRenderer.end();
  }

}
