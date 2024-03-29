package com.leisure.duncraw.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.leisure.duncraw.art.chara.states.DashState;
import com.leisure.duncraw.art.chara.states.InteractState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.chara.states.dark.InfuseDarknessSkill;
import com.leisure.duncraw.art.chara.states.dark.ShadowCloakSkill;
import com.leisure.duncraw.data.SaveData;
import com.leisure.duncraw.data.Settings.DesktopControls;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.screen.Screen;

public class GameScreenInput extends GameScreen implements InputProcessor {
  private int pressedKey = -1;
  private Vector3 cameraStartDrag = Vector3.Zero;
  private Vector2 startDrag = Vector2.Zero;
  private boolean steerMode = false;
  private final DesktopControls desktopControls;
  public GameScreenInput(SaveData save) {
    super(save);
    desktopControls = save.settings.desktopControls;
    InputMultiplexer inputMultiplexer = new InputMultiplexer(debugManager.tooling, new HudInput(hudManager, desktopControls), hudManager.stage, this);
    Gdx.input.setInputProcessor(inputMultiplexer);
  } 
  @Override public void render(float delta) { 
    if (!hudManager.isModalVisible()) update();
    super.render(delta);
  }
  private void update() {
    if (player.movement.isMoving()) return;
    if (Gdx.input.isKeyPressed(desktopControls.down)) { player.setState(new MoveState(0, -1, true, steerMode)); }
    else if (Gdx.input.isKeyPressed(desktopControls.up)) { player.setState(new MoveState(0, 1, true, steerMode)); }
    else if (Gdx.input.isKeyPressed(desktopControls.left)) { player.setState(new MoveState(-1, 0, true, steerMode)); }
    else if (Gdx.input.isKeyPressed(desktopControls.right)) { player.setState(new MoveState(1, 0, true, steerMode)); }
  }
  @Override public Screen next() {
    return new EndingScreenInput();
  }
  @Override public boolean keyDown(int keycode) {
    // Logger.log("GameScreenInput", "Keydown");
    pressedKey = keycode;
    if (pressedKey == desktopControls.dash) player.setState(new DashState());
    return true;
  }
  @Override public boolean keyUp(int keycode) {
    // Logger.log("GameScreenInput", "Keyup");
    if (pressedKey == desktopControls.cancel) hudManager.closeModal();
    else if (pressedKey == desktopControls.menuWindow) hudManager.toogleModal(hudManager.windowUi);
    // else if (pressedKey == desktopControls.inventory) hudManager.toogleModal(hudManager.inventoryHud);
    else if (pressedKey == desktopControls.steerMode) steerMode = !steerMode; 
    else if (pressedKey == desktopControls.action) player.setState(new InteractState(), true);
    else if (pressedKey == desktopControls.skill1) player.setState(new InfuseDarknessSkill());
    else if (pressedKey == desktopControls.skill2) player.setState(new ShadowCloakSkill());
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
