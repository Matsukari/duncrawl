package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.leisure.duncraw.data.UiData;

public class ProgressionWindow extends Hud {
  public MapHud mapHud;
  public ProgressionWindow(MapHud mapHud) {
    this.mapHud = mapHud;
    add(mapHud).fill();
  }
  @Override
  public Hud init(Stage stage, UiData data, ShapeRenderer renderer) {
    mapHud.init(stage, data, renderer);
    return super.init(stage, data, renderer);
  }
  @Override
  public void drawShapes() {
    mapHud.drawShapes();
  }
  @Override
  public void update() {
    mapHud.update();
  }
}
