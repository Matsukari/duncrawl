package com.leisure.duncraw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.leisure.duncraw.art.chara.states.DashState;
import com.leisure.duncraw.art.chara.states.InteractState;
import com.leisure.duncraw.art.chara.states.dark.InfuseDarknessSkill;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.data.Settings;
import com.leisure.duncraw.data.Settings.DesktopControls;
import com.leisure.duncraw.screen.GameScreen;

public class GameScreenInput extends GameScreen implements InputProcessor {
  private int pressedKey = -1;
  private Vector3 cameraStartDrag = Vector3.Zero;
  private Vector2 startDrag = Vector2.Zero; 
  private final DesktopControls desktopControls;
  public GameScreenInput(SaveData save) {
    super(save);
    Gdx.input.setInputProcessor(this);
    desktopControls = save.settings.desktopControls;
  } 
  @Override public void render(float delta) { 
    update();
    super.render(delta);
  }
  private void update() {
    if (player.movement.isMoving()) return;
    if (Gdx.input.isKeyPressed(desktopControls.down)) { player.moveBy(0, -1); }
    else if (Gdx.input.isKeyPressed(desktopControls.up)) { player.moveBy(0, 1); }
    else if (Gdx.input.isKeyPressed(desktopControls.left)) { player.moveBy(-1, 0); }
    else if (Gdx.input.isKeyPressed(desktopControls.right)) { player.moveBy(1, 0); }
  }
  @Override public boolean keyDown(int keycode) {
    // Logger.log("GameScreenInput", "Keydown");
    pressedKey = keycode;
    if (pressedKey == desktopControls.dash) player.setState(new DashState());
    return true;
  }
  @Override public boolean keyUp(int keycode) {
    // Logger.log("GameScreenInput", "Keyup");
    if (pressedKey == desktopControls.confirm) {
      if (hudManager.dialogueHud.isVisible() && !hudManager.dialogueHud.next()) { hudManager.dialogueHud.setVisible(false); }
      else if (pressedKey == desktopControls.action) player.setState(new InteractState());
    }
    else if (pressedKey == desktopControls.action) {
      player.setState(new InteractState());
    }
    else if (pressedKey == desktopControls.skill1) {
      player.setState(new InfuseDarknessSkill());
    }
    pressedKey = -1;
    return true;
  }
  @Override public boolean scrolled(float amountX, float amountY) {
    camera.zoom += amountY;
    return true;
  }
  @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    blockCamera = false;
    return false;
  }
  @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    blockCamera = true;
    cameraStartDrag.x = camera.position.x;
    cameraStartDrag.y = camera.position.y;
    startDrag.x = screenX;
    startDrag.y = screenY;
    return false;
  }
  @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
    camera.position.x = cameraStartDrag.x + (startDrag.x - screenX) * (camera.zoom * 0.5f);
    camera.position.y = cameraStartDrag.y + (screenY - startDrag.y) * (camera.zoom * 0.5f);
    return false;
  }
  @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
    return false;
  }
  @Override public boolean mouseMoved(int screenX, int screenY) {
    return false;
  }
  @Override public boolean keyTyped(char character) {
    return false;
  }
}
