package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.hud.DialogueHud;

public class HudManager {
  public final DialogueHud dialogueHud;
  public SpriteBatch batch;
  public HudManager() {
    batch = new SpriteBatch();
    dialogueHud = new DialogueHud();
  }
  public void renderAvailable(Camera camera) {
    batch.setProjectionMatrix(camera.projection);
    batch.begin();
    if (dialogueHud.canRender()) dialogueHud.draw(batch, 1f);
    batch.end();
  }
}
