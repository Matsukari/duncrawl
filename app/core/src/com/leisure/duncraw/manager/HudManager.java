package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.leisure.duncraw.data.UiData;
import com.leisure.duncraw.hud.DialogueHud;
import com.leisure.duncraw.hud.InventoryHud;
import com.leisure.duncraw.hud.MapHud;
import com.leisure.duncraw.hud.StatusHud;
import com.leisure.duncraw.hud.WindowUi;
import com.leisure.duncraw.screen.GameScreen;

public class HudManager {
  public ShapeRenderer shapeRenderer;
  public final InventoryHud inventoryHud;
  public final DialogueHud dialogueHud;
  public final StatusHud statusHud;
  public final MapHud mapHud;
  public final WindowUi windowUi;
  public SpriteBatch batch;
  public Stage stage;
  public Table root;
  public HudManager(GameScreen game, UiData data) {
    batch = new SpriteBatch();
    stage = new Stage();
    shapeRenderer = new ShapeRenderer();
    dialogueHud = new DialogueHud();
    inventoryHud = new InventoryHud(game.player.inventory, batch);
    statusHud = new StatusHud(game.player);
    mapHud = new MapHud(game.player, game.floorManager);
    windowUi = new WindowUi();
    inventoryHud.init(stage, data, shapeRenderer);
    dialogueHud.init(stage, data, shapeRenderer);
    statusHud.init(stage, data, shapeRenderer);
    mapHud.init(stage, data, shapeRenderer);
    windowUi.init(stage, data, shapeRenderer);
    root = new Table();
    root.setFillParent(true);
    root.add(statusHud).left().top().expand();
    root.add(inventoryHud).center().expand().fill();
    root.add(mapHud).right().top().expand();
    root.row();
    root.add(windowUi).center();
    root.add(dialogueHud).center().bottom().expand();
    stage.addActor(root);
    root.debugAll();
  }
  public void update(float dt) {
    dialogueHud.update();
    statusHud.update();
    mapHud.update();
    windowUi.update();
    inventoryHud.update();
    stage.act(dt);
  }
  public void renderAvailable(Camera camera) {
    // shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    statusHud.drawShapes();
    mapHud.drawShapes();
    dialogueHud.drawShapes();
    inventoryHud.drawShapes();
    windowUi.drawShapes();
    stage.draw();
  }
  public void dispose() {
    stage.dispose();
  }
}
