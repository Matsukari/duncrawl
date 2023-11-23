package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;

public class SplashScreen extends ScreenAdapter {
  public SplashScreen(ScreenChanger changer) {
    changer.change(new MenuScreen(changer));
  }
  @Override
  public void render(float delta) {
  }
}
