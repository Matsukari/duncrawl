package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;

public class HurtState extends State {
  public Chara attacker;
  public HurtState(Chara attacker) { this.attacker = attacker; }
  @Override
  public void update(float dt) {

  }
  
}
