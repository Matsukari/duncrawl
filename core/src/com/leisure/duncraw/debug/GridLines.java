package com.leisure.duncraw.debug;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leisure.duncraw.logging.Logger;

public class GridLines extends RenderDebug {
  public int cols;
  public int rows;
  public int stepX;
  public int stepY;
  public GridLines(int cols, int rows, int stepX, int stepY) {
    Logger.log("GridLines", String.format("Cols: %d, rows: %d, stepX: %d, stepY: %d", 
      cols, rows, stepX, stepY));
    color = Color.GRAY;
    this.cols = cols;
    this.rows = rows;
    this.stepX = stepX;
    this.stepY = stepY;
  }
  @Override
  public void render(ShapeRenderer renderer) {
    renderer.begin(ShapeType.Line);
    renderer.setColor(color);
    for (int x = 0; x <= cols; x++) {
      renderer.line(x * stepX, 0f, x * stepX, rows * stepY);
      for (int y = 0; y <= rows; y++) {
        renderer.line(0f, y * stepY, cols * stepX, y * stepY);
      }
    }
    renderer.end();
  }
 
} 
