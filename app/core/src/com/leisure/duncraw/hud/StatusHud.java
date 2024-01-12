package com.leisure.duncraw.hud;

import java.util.Vector;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.art.chara.Player;

import lib.math.Pointi;

public class StatusHud extends Hud {
  protected final Label rankLabel;
  protected Player player;
  public Pointi attrMargin = new Pointi(10, 20);
  public Vector2 attrScale = new Vector2(1.3f, 5f);
  public float attrSpaceY = 2;
  public Color staminaColor = Color.valueOf("#898675");
  public Color healthColor = Color.valueOf("#db3a3a");
  public StatusHud(Player player) {
    this.player = player;
    // add(rankLabel).left().top().padTop(20);
    setVisible(true);  
  }
  protected StatusHud() {

  }
  {
    rankLabel = createLabel("Beginner");

  }
  @Override
  public void drawShapes() {
    shapeRenderer.begin(ShapeType.Filled);
    shapeRenderer.setColor(healthColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY()+30, player.status.health * attrScale.x, attrScale.y);
    shapeRenderer.setColor(staminaColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY()+23, player.status.stamina * attrScale.x, attrScale.y);
    shapeRenderer.end();
  }

}
