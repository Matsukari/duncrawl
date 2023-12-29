package com.leisure.duncraw.map.generator;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.objs.Lamp;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.TerrainSet;

import lib.math.Pointi;

public class LightSourcesFurnisher extends TerrainFurnisher {
  // distance between two lights (in tiles)
  public Pointi nextOffset = new Pointi(40, 100);
  public int lastLight = 0;
  public int lastOffset = 0;
  
  private LightEnvironment lightEnvironment;
  private EffectManager effectManager;
  public LightSourcesFurnisher(LightEnvironment lightEnvironment, EffectManager effectManager) {
    this.lightEnvironment = lightEnvironment;
    this.effectManager = effectManager;
  }
  @Override
  public void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y) {
    if (lastLight >= lastOffset && terrain.type.equals("wall")) {
      terrainSet.putObject(new Lamp("dat/obj/lamp.dat", lightEnvironment, effectManager), x, y); 
      lastLight = 0;
      lastOffset = MathUtils.random(nextOffset.x, nextOffset.y);
    }
    lastLight++;
  }
  
}
