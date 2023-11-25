package com.leisure.duncraw;

import com.badlogic.gdx.InputProcessor;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.screen.MenuScreen;

public class MenuScreenInput extends MenuScreen implements InputProcessor {
  public MenuScreenInput() {
    next = new GameScreenInput(AssetSource.getSaveData());
  }   
  @Override
  public boolean keyDown(int keycode) {
    return true;
  }
  @Override
  public boolean keyUp(int keycode) {
    return true;
  }
  @Override
  public boolean scrolled(float amountX, float amountY) {
    return false;
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

