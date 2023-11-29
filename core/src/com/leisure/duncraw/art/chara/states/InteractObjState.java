package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.map.Obj;

public class InteractObjState extends State {
  public Obj target;
  public InteractObjState(Obj target) { this.target = target; }
  @Override
  public void update(float dt) {

  }
  
}

