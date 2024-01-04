package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.lighting.Lighting;
import com.leisure.duncraw.art.lighting.PointLight;

public class IlluminateBehaviour extends Observer {
  public PointLight light;
  private Lighting lighting;
  public IlluminateBehaviour(Lighting lighting, PointLight light) {
    this.lighting = lighting;
    this.light = light;
    this.light = lighting.getEnv().addLight(light);
  }
  @Override
  public void invoke(State state) {
  }
  @Override
  public void update() {
    // Logger.log("IlluminateBehaviour", String.format("at %f %f", light.bounds.x, light.bounds.y));
    light.centerTo(chara);
  }
  @Override
  public Observer copy() {
    return new IlluminateBehaviour(lighting, light);
  } 
}
