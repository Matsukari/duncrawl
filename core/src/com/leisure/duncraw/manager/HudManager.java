package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leisure.duncraw.hud.DialogueHud;

public class HudManager {
  public final DialogueHud dialogueHud;
  public SpriteBatch batch;
  public Stage stage;
  public Table root;
  public HudManager(Viewport viewport) {
    batch = new SpriteBatch();
    stage = new Stage();
    dialogueHud = new DialogueHud();
    root = new Table();
    root.setFillParent(true);
    root.add(dialogueHud).bottom().expand();

    stage.addActor(root);
  }
  public void update(float dt) {
    stage.act(dt);
  }
  public void renderAvailable(Camera camera) {
    stage.draw();
  }
  public void dispose() {
    stage.dispose();
  }
}
