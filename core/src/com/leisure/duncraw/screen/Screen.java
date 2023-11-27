package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;

public class Screen extends ScreenAdapter {
  public boolean hasChanged = false;
  public void onChanged() {}
  public Screen next() { return null; }
  public void change() { hasChanged = true; }
}
