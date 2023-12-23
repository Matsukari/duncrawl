package com.leisure.duncraw.art.chara.observers;

import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.lighting.PointLight;
import com.leisure.duncraw.logging.Logger;

public class IlluminateBehaviour extends Observer {
  public PointLight light;
  private LightEnvironment environment;
  public IlluminateBehaviour(LightEnvironment environment, PointLight light) {
    this.environment = environment;
    this.light = light;
    this.light = environment.addLight(light);
  }
  @Override
  public void invoke(State state) {
  }
  @Override
  public void update() {
    // Logger.log("IlluminateBehaviour", String.format("at %f %f", light.bounds.x, light.bounds.y));
    light.centerTo(chara.bounds);
  }
  @Override
  public Observer copy() {
    return new IlluminateBehaviour(environment, light);
  } 
}
