package com.leisure.duncraw.art.chara.observers;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.leisure.duncraw.art.InterpolationEffect;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.observers.dark.InfuseDarknessBehaviour;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.DashAttackState;
import com.leisure.duncraw.art.chara.states.DashState;
import com.leisure.duncraw.art.chara.states.DeathState;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.effs.KnockbackEffect;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.art.gfx.GfxInterpolation;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.EffectManager;

public class AnimationBehaviour extends Observer {
  private final EffectManager effectManager;
  private GfxAnimation attackEffect;
  private DirAnimation attackEffAnim;
  private boolean inIdle = false;
  private int inIdleSkip = 0;
  public AnimationBehaviour(EffectManager effectManager) { this.effectManager = effectManager; }
  @Override
  public void init(Chara c) {
    super.init(c);
  }
  @Override
  public void invoke(State state) {
    // Logger.log("AnimationBehaviour", "Invoke");
    if (state instanceof MoveState && ((MoveState)state).relative) chara.anims.set("move", chara.movement.velX, chara.movement.velY);
    else if (state instanceof DashState) chara.anims.set("dash", chara.movement.velX, chara.movement.velY);
    else if (state instanceof IdleState) {
      inIdle = true;
      // chara.anims.set("idle", chara.movement.lastVelX, chara.movement.lastVelY);
    }
    else if (state instanceof DeathState) {
      effectManager.start(new GfxInterpolation(chara, Interpolation.fade, 1));
    }
    else if (state instanceof HurtState) {
      HurtState s = (HurtState)state;
      Chara attacker = s.attacker;
      Chara defender = s.chara;
      chara.anims.set("hurt", chara.movement.lastVelX, chara.movement.lastVelY);
      Logger.log("AnimationBehaviour", "hurt");
      if (((HurtState)state).knockback && chara.dat.knockable)
        effectManager.start(new KnockbackEffect(
            defender, attacker.movement.lastVelX * (defender.bounds.width/2), attacker.movement.lastVelY * (defender.bounds.height/2), Interpolation.fade, 0.16f));
    }
    else if (state instanceof AttackState || state instanceof DashAttackState) {
      DirAnimation attackAnim = chara.anims.get("attack");
      if (attackAnim != null) {
        attackAnim.setPlayMode(PlayMode.NORMAL);
        attackAnim.replay();
        chara.anims.set("attack", chara.movement.lastVelX, chara.movement.lastVelY);
      }
      InfuseDarknessBehaviour infuseDarknessBehaviour = chara.observers.get(InfuseDarknessBehaviour.class);
      if (infuseDarknessBehaviour != null && infuseDarknessBehaviour.isRunning()) attackEffAnim = chara.anims.get("skill1_strike");
      else attackEffAnim = chara.anims.get("attack_effect");
      
      float width = chara.bounds.width;
      if (state instanceof DashAttackState) width *= 2;
      // AttackState attackState = (AttackState)state;
      attackEffAnim.face(chara.movement.lastVelX, chara.movement.lastVelY);
      attackEffect = new GfxAnimation(attackEffAnim.currentDir, false);
      attackEffect.rotation = MathUtils.random(0, 90);
      attackEffect.bounds.setSize(width);
      attackEffect.centerTo(
          chara.getWorldX() + chara.movement.lastVelX * (chara.bounds.width*0.7f), 
          chara.getWorldY() + chara.movement.lastVelY * (chara.bounds.height*0.7f), 
          chara.bounds.width, chara.bounds.height);
      effectManager.start(attackEffect);
    }
  }
  @Override
  public void update() {
    if (inIdle) {
      if (inIdleSkip >= 1) {
        inIdle = false;
        inIdleSkip = 0;
      }
      inIdleSkip++;
      
    }
  }
  @Override
  public Observer copy() {
    return new AnimationBehaviour(effectManager);
  }
}
