package com.leisure.duncraw.art.chara.states;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.story.chapters.Chapter1;

public class HurtState extends State {
  public Chara attacker;
  public boolean knockback;
  public HurtState(Chara attacker, boolean knockback) { 
    this.attacker = attacker; 
    this.knockback=  knockback;
  }
  public HurtState(Chara attacker) {
    this.attacker = attacker;
    this.knockback = MathUtils.randomBoolean(0.36f);
  }
  @Override
  public void update(float dt) {

  }
  
}
