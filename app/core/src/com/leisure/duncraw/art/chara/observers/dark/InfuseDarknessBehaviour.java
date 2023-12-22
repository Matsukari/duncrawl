package com.leisure.duncraw.art.chara.observers.dark;

import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.chara.states.dark.InfuseDarknessSkill;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.manager.EffectManager;
import lib.time.TimePeeker;

public class InfuseDarknessBehaviour extends Observer {
  private TimePeeker timer = new TimePeeker();
  private TimePeeker sustainTimer = new TimePeeker();
  private boolean invoked = false;
  private EffectManager effectManager;
  public GfxAnimation effect; 
  public int stack = 1;
  public int cost = 10; // per second
  public InfuseDarknessBehaviour(EffectManager effectManager) {
    this.effectManager = effectManager;
  }
  public InfuseDarknessBehaviour() { effectManager = null; }
  @Override
  public void invoke(State state) {
    if (state instanceof InfuseDarknessSkill && chara.status.canDo(cost)) {
      if (invoked && isActive()) stack++;
      timer.peek();
      sustainTimer.peek();
      invoked = true;
      if (effectManager != null && stack == 1) {
        effect = new GfxAnimation(null, false);
        effect.bounds.setPosition(chara.bounds.x, chara.bounds.y);
        effect.bounds.setSize(chara.bounds.width);
        changeEffectDir();
        effectManager.start(effect);
      }
    }
    else if (invoked && isActive()) {
      if (state instanceof MoveState) changeEffectDir();
      else if (state instanceof AttackState) { 
        AttackState attackState = (AttackState)state;
        attackState.chara.status.bonusAttack = getDamage();
      }  
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
    else effect.bounds.setPosition(chara.bounds.x, chara.bounds.y);
  }
  @Override
  public Observer copy() {
    return new InfuseDarknessBehaviour();
  }
  public void stop() {
    if (effectManager != null) effectManager.stop(effect);
    chara.status.bonusAttack = 0;
    invoked = false;
    stack = 1;
  }
  public void changeEffectDir() {
    DirAnimation dirAnimation = chara.anims.get("skill1");
    if (dirAnimation != null) {
      dirAnimation.face(chara.movement.lastVelX, chara.movement.lastVelY);
      effect.anim = dirAnimation.currentDir;
      effect.loop = true;
    }
  }
  public boolean isRunning() { return invoked && isActive(); }
  public boolean isActive() { return timer.sinceLastPeek() <= getDuration() * 1000f; }
  public float getDamage() { return (chara.status.elementPower + stack * 0.5f); }
  public float getDuration() { return chara.status.elementPower + stack * 0.2f; }
}
