package com.leisure.duncraw.art.chara.states.dark;

import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.manager.EffectManager;

public class ShadowTeleportSkill extends State { 
  public EffectManager effectManager;
  public Gfx effect; 
  public Vector2 dst;
  public Chara target;
  public ShadowTeleportSkill(Chara target, Vector2 dst) {
    this.target = target; 
    this.dst = dst;
  }
  @Override
  public void init(Chara s) {
    super.init(s);  
  }
  @Override
  public void update(float dt) {
  }
}
