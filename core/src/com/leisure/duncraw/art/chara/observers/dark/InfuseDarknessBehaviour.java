package com.leisure.duncraw.art.chara.observers.dark;

import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.dark.InfuseDarknessSkill;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.manager.EffectManager;
import lib.time.TimePeeker;

public class InfuseDarknessBehaviour extends Observer {
  private TimePeeker timer = new TimePeeker();
  private boolean invoked = false;
  private EffectManager effectManager;
  public Gfx effect; 
  public int stack = 1;
  public InfuseDarknessBehaviour(EffectManager effectManager) {
    this.effectManager = effectManager;
  }
  public InfuseDarknessBehaviour() { effectManager = null; }
  @Override
  public void invoke(State state) {
    if (state instanceof InfuseDarknessSkill) {
      if (invoked && isActive()) stack++;
      timer.peek();
      invoked = true;
      DirAnimation dirAnimation = chara.anims.get("skill1");
      if (dirAnimation != null && effectManager != null && stack == 1) {
        dirAnimation.face(chara.movement.lastVelX, chara.movement.lastVelY);
        effect = new GfxAnimation(effectManager.batch, dirAnimation.currentDir, true);
        effect.bounds.setPosition(chara.bounds.x, chara.bounds.y);
        effect.bounds.setSize(chara.bounds.width);
        effectManager.start(effect);
      }
    }
    else if (state instanceof AttackState && invoked && isActive()) { 
      AttackState attackState = (AttackState)state;
      attackState.chara.status.bonusAttack = getDamage();
    } 
  }
  @Override
  public void update() {
    if (!invoked) return;
    if (!isActive()) {
      if (effectManager != null) effectManager.stop(effect);
      chara.status.bonusAttack = 0;
      stack = 1;
      invoked = false;
    } else {
      effect.bounds.setPosition(chara.bounds.x, chara.bounds.y);
    }
  }
  @Override
  public Observer copy() {
    return new InfuseDarknessBehaviour();
  }
  public boolean isActive() { return timer.sinceLastPeek() <= getDuration() * 1000f; }
  public float getDamage() { return (chara.status.elementPower + stack * 0.5f); }
  public float getDuration() { return chara.status.elementPower + stack * 0.2f; }
}
