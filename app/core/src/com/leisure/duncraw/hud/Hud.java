package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.leisure.duncraw.data.UiData;

public class Hud extends Table {
  protected Stage stage;
  protected UiData data;
  protected ShapeRenderer shapeRenderer;
  public void init(Stage stage, UiData data, ShapeRenderer renderer) {
    this.stage = stage;
    this.data = data;
    this.shapeRenderer = renderer;
    onInit();
  }
  protected void onInit() {}
  public void drawShapes() {}
  public void update() {}
}
