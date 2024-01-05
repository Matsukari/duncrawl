package com.leisure.duncraw.map.generator;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.objs.Lamp;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.TerrainSet;

public class LightSourcesFurnisher extends TerrainFurnisher {
  private LightEnvironment lightEnvironment;
  private EffectManager effectManager;
  public LightSourcesFurnisher(LightEnvironment lightEnvironment, EffectManager effectManager) {
    this.lightEnvironment = lightEnvironment;
    this.effectManager = effectManager;
  }
  @Override
  public void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y) {
    if (genDistrib(40, 100, 0) && terrain.type.equals("wall")) {
      terrainSet.putObject(new Lamp(Graphics.objsSources.lamp, lightEnvironment, effectManager), x, y); 
    }
  }
  
}
