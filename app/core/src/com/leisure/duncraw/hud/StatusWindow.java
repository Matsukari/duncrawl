package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.leisure.duncraw.data.UiData;
import com.leisure.duncraw.logging.Logger;

public class StatusWindow extends Hud { 
  public StatusHud statusHud;
  public InventoryHud inventoryHud;
  public StatusWindow(StatusHud statusHud, InventoryHud inventoryHud) {
    this.statusHud = statusHud;
    this.inventoryHud = inventoryHud;
    add(statusHud).center().top().expand();
    add(inventoryHud).center().top().expand();
    inventoryHud.setVisible(true);
  }
  @Override
  public Hud init(Stage stage, UiData data, ShapeRenderer renderer) {
    statusHud.init(stage, data, renderer);
    inventoryHud.init(stage, data, renderer);
    return super.init(stage, data, renderer);
  }
  @Override
  public void drawShapes() {
    statusHud.drawShapes();
    inventoryHud.drawShapes();
  }
  @Override
  public void update() {
    statusHud.update();
    inventoryHud.update();
  }
}
