package com.leisure.duncraw.art.lighting;

import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.Floor;

// Whenever LightEnvironment is changed due to floor changes
public class Lighting {
  private Floor lastFloor = null;
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
    if (lastFloor == null || lastFloor.isExactSame(manager.getFloor())) {
      lastFloor = manager.getFloor();
      lightEnvironment = manager.getFloor().lightEnvironment;
    }
  }
  
}
