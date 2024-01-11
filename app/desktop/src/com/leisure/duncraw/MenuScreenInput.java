package com.leisure.duncraw;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.data.AssetSource;
import com.leisure.duncraw.hud.Hud;
import com.leisure.duncraw.screen.MenuScreen;
import com.leisure.duncraw.screen.Screen;

public class MenuScreenInput extends MenuScreen implements InputProcessor {
  public Stage stage;
  public Table root;
  public Label options[] = new Label[3]; 
  public MenuScreenInput() {
    stage = new Stage();
    root = new Table();
    root.setFillParent(true);
    options[0] = Hud.createLabel("Play game");
    options[0].addListener(new ClickListener() {
      @Override
      public void clicked(InputEvent event, float x, float y) {
        change();
      }
    });
    options[1] = Hud.createLabel("Options");
    options[2] = Hud.createLabel("Credits");
    for (Label option : options) {
      root.add(option).center().expand();
      root.row();
    }
    
    stage.addActor(root);
  }
  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);
    stage.act();
    stage.draw();
  }
  @Override
  public Screen next() {
    return new GameScreenInput(AssetSource.getSaveData());
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

