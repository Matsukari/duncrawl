package com.leisure.duncraw.manager;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.leisure.duncraw.data.UiData;
import com.leisure.duncraw.hud.DialogueHud;
import com.leisure.duncraw.hud.Hud;
import com.leisure.duncraw.hud.InventoryHud;
import com.leisure.duncraw.hud.MapFull;
import com.leisure.duncraw.hud.MapHud;
import com.leisure.duncraw.hud.QuestFull;
import com.leisure.duncraw.hud.StatusFull;
import com.leisure.duncraw.hud.StatusHud;
import com.leisure.duncraw.hud.WindowUi;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;

public class HudManager {
  public ShapeRenderer shapeRenderer;
  // public final InventoryHud inventoryHud;
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
    // inventoryHud = new InventoryHud(game.player.inventory, batch);
    statusHud = new StatusHud(game.player);
    mapHud = new MapHud(game.player, game.floorManager);
    windowUi = new WindowUi(
        new StatusFull(game.player), 
        new InventoryHud(game.player.inventory, batch), 
        new MapFull(game.player, game.floorManager),
        new QuestFull(game.storyManager));
    // inventoryHud.init(stage, data, shapeRenderer, batch);
    dialogueHud.init(stage, data, shapeRenderer, batch);
    statusHud.init(stage, data, shapeRenderer, batch);
    mapHud.init(stage, data, shapeRenderer, batch);
    windowUi.init(stage, data, shapeRenderer, batch);
    root = new Table();
    Table row = new Table();
    root.setFillParent(true);
    root.padTop(5);
    row.add(statusHud).left().top().expandX().height(100).padLeft(10);
    row.add(mapHud).right().top().expandX().height(100).padRight(10);
    root.add(row).fillX().expandX();
    
    Stack stack = new Stack(windowUi); 
    // inventoryHud.setSize(stage.getWidth()-200, 400);
    windowUi.setSize(stage.getWidth()-200, 500);
    root.row().expand();
    root.add(stack).center().fill().expand().padLeft(100).padRight(100).height(500);

    root.row().expand();
    root.add(dialogueHud).bottom().expandX().height(100);
    
    stage.addActor(root);
    // root.debugAll();
  }
  // Permission to pop modals
  public void toogleModal(Hud modal) { 
    boolean visible = modal.isVisible();
    windowUi.setVisible(false);
    dialogueHud.setVisible(false);
    // inventoryHud.setVisible(false);
    modal.setVisible(!visible);
    // float x = modal.getWidth();
    // float y = modal.getHeight();
    // modal.setSize(0, 0);
    modal.addAction(Actions.alpha(0.2f));
    modal.addAction(Actions.alpha(1f, 0.5f, Interpolation.fade)); 
    Logger.log("HudManager", "toogleModal");
  }
  public void closeModal() {
    windowUi.setVisible(false);
    dialogueHud.setVisible(false);
    // inventoryHud.setVisible(false);
  }
  public boolean isModalVisible() {
    return windowUi.isVisible() || dialogueHud.isVisible();
  }
  public void update(float dt) {
    dialogueHud.update();
    statusHud.update();
    mapHud.update();
    windowUi.update();
    // inventoryHud.update();
    stage.act(dt);
  }
  public void renderAvailable(Camera camera) {
    Gdx.gl.glEnable(GL20.GL_BLEND);
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    statusHud.drawShapes();
    mapHud.drawShapes();
    dialogueHud.drawShapes();
    // inventoryHud.drawShapes();
    windowUi.drawShapes();
    stage.draw();
  }
  public void dispose() {
    stage.dispose();
  }
}
