package com.leisure.duncraw.art.chara.states.dark;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.manager.EffectManager;

public class ShadowCloakSkill extends State {
  public EffectManager effectManager;
  public Gfx effect; 
  public ShadowCloakSkill() {
  }
  @Override
  public void init(Chara s) {
    super.init(s);  
  }
  @Override
  public void update(float dt) {
  }


  
}
