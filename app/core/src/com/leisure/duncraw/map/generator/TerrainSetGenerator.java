package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.map.TerrainSet;

public class TerrainSetGenerator {
  public static TerrainSet gen(int level) {
    return null;
  }
  public static void combine(TerrainSet base, TerrainSet add) {
    
  }
  public static TerrainSet blank() {
    return new TerrainSet(Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/32, 32, 32);
  }
  public static ArrayList<Obj> selectExits(TerrainSet terrainSet) {
    ArrayList<Obj> exits = new ArrayList<>();
    return exits;
  }
}
