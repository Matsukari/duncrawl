
package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.map.Terrain;

public class MineState extends State {
  public Terrain target;
  public MineState(Terrain target) { this.target = target; }
  @Override
  public void update(float dt) {

  }
  
}
