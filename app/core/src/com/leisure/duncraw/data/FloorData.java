package com.leisure.duncraw.data;

import com.badlogic.gdx.math.Vector2;

public class FloorData extends Dat {
  public int roomsNum;
  public int tileMaxRows;
  public int tileMaxCols;
  public int tileSize;
  public Vector2 widthRange;
  public Vector2 heightRange;
  public int maxMob;
  public int maxElite;
  @Override
  public void reset() {
    roomsNum = 5;
    tileMaxCols = 60;
    tileMaxRows = 60;
    tileSize = 32;
    widthRange = new Vector2(0.2f, 1f);
    heightRange = new Vector2(0.2f, 1f);
    maxMob = 15;
    maxElite = 5;
  }
  public float getMaxWidth() { return tileMaxCols * tileSize; }
  public float getMaxHeight() { return tileMaxRows * tileSize; }

}
