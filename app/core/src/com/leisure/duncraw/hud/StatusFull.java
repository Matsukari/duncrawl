package com.leisure.duncraw.hud;

import java.nio.file.StandardWatchEventKinds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leisure.duncraw.art.chara.Player;

import lib.math.Pointi;

public class StatusFull extends StatusHud {
  public Color slotColor = Color.valueOf("#000000");
  public float equipSlotSize = 30;
  public float equipItemPadding = 10;
  public Pointi equipMargin = new Pointi(10, 10);
  public StatusFull(Player player) {
    this.player = player;
    // add(rankLabel).left().top().padTop(20);
    setVisible(true);  
  }
  @Override
  public void drawShapes() {
    shapeRenderer.begin(ShapeType.Filled);
    float stackY = getGlobalY() + getHeight();
    shapeRenderer.setColor(healthColor);
    shapeRenderer.rect(getGlobalX(), stackY, player.status.health * attrScale.x, attrScale.y);
    stackY-=attrScale.y+attrSpaceY;
    shapeRenderer.setColor(staminaColor);
    shapeRenderer.rect(getGlobalX(), stackY, player.status.stamina * attrScale.x, attrScale.y);

    stackY-=attrMargin.y+equipSlotSize+equipMargin.y;
    shapeRenderer.setColor(slotColor);
    shapeRenderer.circle(getGlobalX() + (equipSlotSize), stackY, equipSlotSize);
    shapeRenderer.circle(getGlobalX() + (equipSlotSize * 2) + equipSlotSize + equipMargin.x, stackY, equipSlotSize);
    shapeRenderer.end();
    batch.begin();
    float itemSize = (equipSlotSize*2)-equipItemPadding*2; 
    if (player.weapon != null) player.weapon.renderStore(batch, getGlobalX()+equipItemPadding, stackY-itemSize/2, itemSize, itemSize);
    if (player.armor != null) player.armor.renderStore(batch, getGlobalX() + equipItemPadding*2 + (equipSlotSize*2) + equipMargin.x + equipItemPadding * 2, stackY-itemSize/2, itemSize, itemSize);
    batch.end();
  }
}
