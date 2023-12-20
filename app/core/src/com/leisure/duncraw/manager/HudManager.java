package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.leisure.duncraw.data.UiData;
import com.leisure.duncraw.hud.DialogueHud;
import com.leisure.duncraw.hud.MapHud;
import com.leisure.duncraw.hud.StatusHud;
import com.leisure.duncraw.hud.WindowUi;
import com.leisure.duncraw.screen.GameScreen;

public class HudManager {
  public ShapeRenderer shapeRenderer;
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
    statusHud = new StatusHud(game.player);
    mapHud = new MapHud();
    windowUi = new WindowUi();
    dialogueHud.init(stage, data, shapeRenderer);
    statusHud.init(stage, data, shapeRenderer);
    mapHud.init(stage, data, shapeRenderer);
    windowUi.init(stage, data, shapeRenderer);
    root = new Table();
    root.setFillParent(true);
    root.add(dialogueHud).bottom().expand();
    root.add(statusHud).left().top().expand();
    root.add(mapHud).right().top().expand();
    root.add(windowUi).center();
    stage.addActor(root);
  }
  public void update(float dt) {
    dialogueHud.update();
    statusHud.update();
    mapHud.update();
    windowUi.update();
    stage.act(dt);
  }
  public void renderAvailable(Camera camera) {
    // shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
    statusHud.drawShapes();
    stage.draw();
  }
  public void dispose() {
    stage.dispose();
  }
}
