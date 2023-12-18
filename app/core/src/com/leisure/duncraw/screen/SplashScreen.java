package com.leisure.duncraw.screen;

public class SplashScreen extends Screen {
  public SplashScreen() { }
  @Override
  public Screen next() { return new MenuScreen(); }
  @Override
  public void render(float delta) {
  }
}
