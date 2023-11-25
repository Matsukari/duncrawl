package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public abstract class Art {
  public Color color = new Color(1, 1, 1, 1);
  public Rectangle bounds = new Rectangle(0, 0, 0, 0);
  public void moveTo(float x, float y) { bounds.setPosition(x, y); }
  public abstract void render();
}
