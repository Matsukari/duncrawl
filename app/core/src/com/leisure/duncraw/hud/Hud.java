package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.data.UiData;

public class Hud extends Table {
  protected Stage stage;
  protected UiData data;
  protected ShapeRenderer shapeRenderer;
  public Hud init(Stage stage, UiData data, ShapeRenderer renderer) {
    this.stage = stage;
    this.data = data;
    this.shapeRenderer = renderer;
    onInit();
    return this;
  }
  public static Label createLabel(String text, String source, Color color) { return new Label(text, new Label.LabelStyle(Graphics.getFont(source), color)); }
  public static Label createLabel(String text) { return new Label(text, new Label.LabelStyle(Graphics.getFont(Graphics.fontSources.def), Color.WHITE)); }
  protected void onInit() {}
  public void drawShapes() {}
  public void update() {}
}
