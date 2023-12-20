package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;

public class MapHud extends Hud {
  private final BitmapFont font;
  private final Label.LabelStyle labelStyle;
  private final Label floorLabel;
  public MapHud() {
    font = Graphics.getFont(Graphics.fontSources.def);
    labelStyle = new Label.LabelStyle(font, Color.WHITE);
    floorLabel = new Label("", labelStyle);
    add(floorLabel).right().top().expand();
    setVisible(true);  
  }

}
