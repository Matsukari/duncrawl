package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.leisure.duncraw.GameApplication;

public class MenuScreen extends ScreenAdapter {
  public MenuScreen(ScreenChanger screenChanger) {
    screenChanger.change(new GameScreen());
  }
  @Override
  public void render(float delta) {
    ScreenUtils.clear(Color.BLACK);
  }
}
