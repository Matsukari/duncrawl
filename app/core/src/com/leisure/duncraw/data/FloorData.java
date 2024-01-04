package com.leisure.duncraw.data;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
  public String title;
  public int normalHeight;
  public String classname;
  public int level;
  public Color envColor;
  public HashMap<String, Integer> customHeight;
  public HashMap<String, String> prefabRooms;
  @Override
  public void reset() {
    roomsNum = 5;
    tileMaxCols = 32;
    tileMaxRows = 32;
    tileSize = 32;
    level = 0;
    widthRange = new Vector2(0.2f, 1f);
    heightRange = new Vector2(0.2f, 1f);
    maxMob = 15;
    maxElite = 5;
    normalHeight = 2;
    envColor = new Color(0.033f, 0.033f, 0.100f, 0.2f);
    customHeight = new HashMap<>();
    prefabRooms = new HashMap<>();
    classname = "com.leisure.duncraw.map.floors.Floor1";
    title = "Default Floor";
  }
  public float getMaxWidth() { return tileMaxCols * tileSize; }
  public float getMaxHeight() { return tileMaxRows * tileSize; }

}
