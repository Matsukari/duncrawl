package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.chara.Player;

public class StatusHud extends Hud {
  private final BitmapFont font;
  private final Label.LabelStyle labelStyle;
  private final Label rankLabel;
  private final Player player;
  public StatusHud(Player player) {
    this.player = player;
    font = Graphics.getFont(Graphics.fontSources.def);
    labelStyle = new Label.LabelStyle(font, Color.WHITE);
    rankLabel = new Label("Beginner", labelStyle);
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
