package com.leisure.duncraw.art.chara.observers.dark;

import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.art.chara.states.dark.ShadowCloakSkill;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.manager.EffectManager;

import lib.time.TimePeeker;

public class ShadowCloakBehaviour extends Observer { 
  private TimePeeker timer = new TimePeeker();
  private TimePeeker sustainTimer = new TimePeeker();
  private boolean invoked = false;
  private EffectManager effectManager;
  public Gfx effect; 
  public int cost = 5; // per second
  public ShadowCloakBehaviour(EffectManager effectManager) {
    this.effectManager = effectManager;
  }
  public ShadowCloakBehaviour() { effectManager = null; }
  @Override
  public void invoke(State state) {
    if (state instanceof ShadowCloakSkill && chara.status.canDo(cost)) {
      if (invoked && isActive()) return;
      timer.peek();
      sustainTimer.peek();
      invoked = true;
      DirAnimation dirAnimation = chara.anims.get("skill2");
      if (dirAnimation != null && effectManager != null) {
        dirAnimation.face(chara.movement.lastVelX, chara.movement.lastVelY);
        effect = new GfxAnimation(dirAnimation.currentDir, true);
        effect.bounds.setSize(chara.bounds.width);
        effectManager.start(effect);
      }
    }
    else if (state instanceof HurtState && invoked && isActive()) { 
      HurtState hurtState = (HurtState)state;
      hurtState.chara.status.bonusDefense = hurtState.chara.status.elementPower;
    } 
  }
  @Override
  public void update() {
    if (!invoked) return;
    if (sustainTimer.sinceLastPeek() >= 1000) {
      if (chara.status.canDo(cost)) chara.status.stamina -= cost;
      else stop(); 
      sustainTimer.peek();
    }
    if (!isActive()) stop(); 
    else effect.centerTo(chara);
  }
  @Override
  public Observer copy() {
    return new ShadowCloakBehaviour();
  }
  public void stop() {
    if (effectManager != null) effectManager.stop(effect);
    chara.status.bonusDefense = 0;
    invoked = false;
  }
  public boolean isActive() { return timer.sinceLastPeek() <= getDuration() * 1000f; }
  public float getDefense() { return chara.status.elementPower * chara.status.phyDefense.x; }
  public float getDuration() { return chara.status.elementPower; }
  
}
