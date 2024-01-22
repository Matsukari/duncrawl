package com.leisure.duncraw.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.hud.Hud;
import com.leisure.duncraw.screen.Screen;

import lib.time.Timer;

public class EndingScreenInput extends Screen implements InputProcessor {

  public Color bgTo = Color.valueOf("#bbbbbb");
  public Color bgFrom = Color.valueOf("#000015");
  public Color bg = bgFrom.cpy();
  public Timer timer = new Timer(5000);
  public boolean sectime = false;
  public Stage stage;
  public Table root;
  public EndingScreenInput() {
    stage = new Stage();
    root = new Table();
    root.setFillParent(true);
    Label label = Hud.createLabel("The game(?) ends here.");
    label.setColor(Color.BLACK);
    root.add(label).center(); 
    stage.addActor(root);
    Gdx.input.setInputProcessor(new InputMultiplexer(stage, this));
    timer.start();
  }
  @Override
  public void render(float delta) {
    if (sectime && timer.isFinished()) {
      change();
    }
    bg.r = Interpolation.fade.apply(bgFrom.r, bgTo.r, timer.normalize());
    bg.g = Interpolation.fade.apply(bgFrom.g, bgTo.g, timer.normalize());
    bg.b = Interpolation.fade.apply(bgFrom.b, bgTo.b, timer.normalize());

    ScreenUtils.clear(bg);
    stage.act();
    stage.draw();
  }
  @Override
  public Screen next() {
    return new MenuScreenInput();
  }
  @Override
  public boolean keyDown(int keycode) {

    return false;
  }
  @Override
  public boolean keyUp(int keycode) {
    if (timer.isFinished()) {
      timer.reset();
      sectime = true; 
      Color temp = bgFrom;
      bgFrom = bgTo;
      bgTo = temp;
    }
    return false;
  }
  @Override
  public boolean scrolled(float amountX, float amountY) {

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
  public boolean mouseMoved(int screenX, int screenY) {

    return false;
  }
  @Override
  public boolean keyTyped(char character) {

    return false;
  }
  @Override
  public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {

    return false;
  }
}
