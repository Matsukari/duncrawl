package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;

public class InventoryHud extends Hud {
  private final BitmapFont font;
  private final Label.LabelStyle labelStyle;
  private final Label rankLabel;
  public InventoryHud() {
    font = Graphics.getFont(Graphics.fontSources.def);
    labelStyle = new Label.LabelStyle(font, Color.WHITE);
    rankLabel = new Label("", labelStyle);
    add(rankLabel).center().bottom().expand();
    setVisible(false);  
  }

}
