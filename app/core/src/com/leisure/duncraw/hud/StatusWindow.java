package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.leisure.duncraw.data.UiData;
import com.leisure.duncraw.logging.Logger;

public class StatusWindow extends Hud { 
  public StatusFull statusHud;
  public InventoryHud inventoryHud;
  public StatusWindow(StatusFull statusHud, InventoryHud inventoryHud) {
    this.statusHud = statusHud;
    this.inventoryHud = inventoryHud;
    add(statusHud).center().left().top().padLeft(20).padTop(10).fill().width(200);
    add(inventoryHud).center().left().top().expand().padRight(20).fill();
    inventoryHud.setVisible(true);
  }
  @Override
  public Hud init(Stage stage, UiData data, ShapeRenderer renderer, SpriteBatch batch) {
    statusHud.init(stage, data, renderer, batch);
    inventoryHud.init(stage, data, renderer, batch);
    return super.init(stage, data, renderer, batch);
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
