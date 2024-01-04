package com.leisure.duncraw.art.lighting;

import com.leisure.duncraw.manager.FloorManager;

// Whenever LightEnvironment is changed due to floor changes
public class Lighting {
  private int lastLevel = 12312;
  private LightEnvironment lightEnvironment;
  private final FloorManager manager;
  public Lighting(FloorManager manager) {
    this.manager = manager;
    updateEnv();
  }
  public LightEnvironment getEnv() {
    return lightEnvironment;
  }
  public void updateEnv() {
    if (lastLevel != manager.getFloor().generator.data.level) {
      lastLevel = manager.getFloor().generator.data.level;
      lightEnvironment = manager.getFloor().lightEnvironment;
    }
  }
  
}
