package com.leisure.duncraw.art.lighting;

import java.util.ArrayList;

import com.leisure.duncraw.manager.FloorManager;
import com.leisure.duncraw.map.Floor;

// Whenever LightEnvironment is changed due to floor changes
public class Lighting {
  private ArrayList<PointLight> persistentLights = new ArrayList<>();
  private Floor lastFloor = null;
  private LightEnvironment lightEnvironment;
  private final FloorManager manager;
  public Lighting(FloorManager manager) {
    this.manager = manager;
  }
  public LightEnvironment getEnv() {
    return lightEnvironment;
  }
  public void updateEnv() {
    // Changed because player have gone onto the next floor
    if (lastFloor == null || !lastFloor.isExactSame(manager.getFloor())) {
      lastFloor = manager.getFloor();
      lightEnvironment = manager.getFloor().lightEnvironment;
      lightEnvironment.lightSources.addAll(persistentLights);
    }
  }
  public PointLight addPersistLight(PointLight light) {
    persistentLights.add(light);
    return getEnv().addLight(light);
  }
  
}
