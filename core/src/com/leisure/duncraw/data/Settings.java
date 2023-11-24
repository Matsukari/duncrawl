package com.leisure.duncraw.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;

public class Settings {
  public Difficulty difficulty;
  public float music;
  public float sfx;
  public float ambient;
  public boolean vSync;
  public Rectangle bounds;
  DesktopControls desktopControls;
  ScreenControls screenControls;
  public void reset() {
    difficulty = Difficulty.NORMAL;
    music = 0.5f;
    sfx = 0.5f;
    ambient = 0.5f;
    vSync = true;
    bounds = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    desktopControls = new DesktopControls();
    desktopControls.reset();
    screenControls = new ScreenControls();
    screenControls.reset();
  }

  public class DesktopControls {
    public int action;
    public int left;
    public int right;
    public int up;
    public int down;
    public int confirm;
    public int cancel;
    public int menuLeft;
    public int menuRight;
    public void reset() {
      action = Keys.J;
      left = Keys.A;
      right = Keys.D;
      up = Keys.W;
      down = Keys.S;
      confirm = Keys.J;
      cancel = Keys.ESCAPE;
      menuLeft = Keys.LEFT;
      menuRight = Keys.RIGHT;
    }
  }
  public class ScreenControls {
    // Offsets to bottom left (as dpad is attached in a stage)
    public float dPadX;
    public float dPadY;
    public float dPadScale;
    public float actionX;
    public float actionY;
    public float actionScale;
    public void reset() {
      dPadX = 0f;
      dPadY = 0f;
      dPadScale = 1f;
      actionX = 0f;
      actionY = 0f;
      actionScale = 1f;
    }
  }
  public enum Difficulty { EASY, NORMAL, HARD, NIGHTMARE };
}
