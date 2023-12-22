package com.leisure.duncraw.data;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
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
  public HashMap<String, Integer> customHeight;
  public HashMap<String, String> prefabRooms;
  public static FloorData fromDat(String source) {
    FloorData data = new FloorData();
    data.reset();
    try { data = Deserializer.load(FloorData.class, Gdx.files.local(source)); } 
    catch (Exception e) { Serializer.save(data, Gdx.files.local(source)); }
    return data;
  }
  @Override
  public void reset() {
    roomsNum = 5;
    tileMaxCols = 32;
    tileMaxRows = 32;
    tileSize = 32;
    widthRange = new Vector2(0.2f, 1f);
    heightRange = new Vector2(0.2f, 1f);
    maxMob = 15;
    maxElite = 5;
    normalHeight = 2;
    customHeight = new HashMap<>();
    prefabRooms = new HashMap<>();
    title = "Default Floor";
  }
  public float getMaxWidth() { return tileMaxCols * tileSize; }
  public float getMaxHeight() { return tileMaxRows * tileSize; }

}
