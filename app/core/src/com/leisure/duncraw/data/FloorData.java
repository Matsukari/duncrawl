package com.leisure.duncraw.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.map.generator.RoomsBuilder;

import lib.math.Pointi;

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
  public ArrayList<String> chests;
  public ArrayList<String> stairs;
  public boolean firstGen;
  public Generation generation;
  public FloorStatistic statistic;
  public static class Generation {
    public static class Entity {
      public int x;
      public int y;
      public String classname;
      public String dat;
    }
    public ArrayList<Entity> entities;
    public RoomsBuilder roomsBuilder;
  }
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
    chests = new ArrayList<>();
    stairs = new ArrayList<>();
    firstGen = true;
    statistic = new FloorStatistic();
    statistic.reset();
    generation = new Generation();
    generation.entities = new ArrayList<>();
    generation.roomsBuilder = new RoomsBuilder(tileSize);
    generation.roomsBuilder.reset();
    classname = "com.leisure.duncraw.map.floors.Floor1";
    title = "Default Floor";
  }
  public float getMaxWidth() { return tileMaxCols * tileSize; }
  public float getMaxHeight() { return tileMaxRows * tileSize; }

}
