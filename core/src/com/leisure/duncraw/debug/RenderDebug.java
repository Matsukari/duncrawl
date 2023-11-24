package com.leisure.duncraw.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class RenderDebug {
  public Color color = Color.BLUE;
  public abstract void render(ShapeRenderer renderer);
}
