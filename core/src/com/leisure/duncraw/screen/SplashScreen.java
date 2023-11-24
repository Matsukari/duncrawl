package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.leisure.duncraw.logging.Logger;

public class SplashScreen extends Screen {
  public SplashScreen() {
    next = new MenuScreen();
  }
  @Override
  public void render(float delta) {
  }
}
