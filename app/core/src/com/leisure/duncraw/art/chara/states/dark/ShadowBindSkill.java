package com.leisure.duncraw.art.chara.states.dark;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.manager.EffectManager;

public class ShadowBindSkill extends State { 
  public EffectManager effectManager;
  public Gfx effect; 
  public Chara target;
  public ShadowBindSkill(Chara target) {
    this.target = target;
  }
  @Override
  public void init(Chara s) {
    super.init(s);  
  }
  @Override
  public void update(float dt) {
  }
}
