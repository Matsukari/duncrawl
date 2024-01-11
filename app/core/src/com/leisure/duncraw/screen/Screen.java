package com.leisure.duncraw.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.leisure.duncraw.logging.Logger;

public class Screen extends ScreenAdapter {
  public boolean hasChanged = false;
  public void onChanged() {}
  public Screen next() { return null; }
  public void change() { hasChanged = true; Logger.log("Screen", "Changed screen"); }
}
