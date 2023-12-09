package com.leisure.duncraw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;

public class GameScreenInput extends GameScreen implements InputProcessor {
  private int pressedKey = -1;
  public GameScreenInput(SaveData save) {
    super(save);
    Gdx.input.setInputProcessor(this);
  } 
  @Override
  public void render(float delta) { 
    update();
    super.render(delta);
  }
  private void update() {
    if (player.movement.isMoving()) return;
    if (Gdx.input.isKeyPressed(saveData.settings.desktopControls.down)) {
      player.moveBy(0, -1);
    }
    else if (Gdx.input.isKeyPressed(saveData.settings.desktopControls.up)) {
      player.moveBy(0, 1);
    }
    else if (Gdx.input.isKeyPressed(saveData.settings.desktopControls.left)) {
      player.moveBy(-1, 0);
    }
    else if (Gdx.input.isKeyPressed(saveData.settings.desktopControls.right)) {
      player.moveBy(1, 0);
    }
  }
  @Override
  public boolean keyDown(int keycode) {
    // Logger.log("GameScreenInput", "Keydown");
    pressedKey = keycode;
    return true;
  }
  @Override
  public boolean keyUp(int keycode) {
    // Logger.log("GameScreenInput", "Keyup");
    if (pressedKey == saveData.settings.desktopControls.confirm) {
      player.interactAhead();
    }
    pressedKey = -1;
    return true;
  }
  @Override
  public boolean scrolled(float amountX, float amountY) {
    camera.zoom += amountY;
    return true;
  }
  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }
  @Override
  public boolean keyTyped(char character) {
    return false;
  }
  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    return false;
  }
  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    return false;
  }
  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    return false;
  }
  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }
}
