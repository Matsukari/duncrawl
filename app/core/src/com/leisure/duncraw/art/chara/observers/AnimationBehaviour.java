package com.leisure.duncraw.art.chara.observers;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.observers.dark.InfuseDarknessBehaviour;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.DashState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.gfx.Gfx;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.EffectManager;

public class AnimationBehaviour extends Observer {
  private final EffectManager effectManager;
  private GfxAnimation attackEffect;
  private DirAnimation attackEffAnim;
  public AnimationBehaviour(EffectManager effectManager) { this.effectManager = effectManager; }
  @Override
  public void init(Chara c) {
    super.init(c);
  }
  @Override
  public void invoke(State state) {
    // Logger.log("AnimationBehaviour", "Invoke");
    if (state instanceof MoveState && ((MoveState)state).relative) chara.anims.set("move", chara.movement.velX, chara.movement.velY);
    // else if (state instanceof DashState) chara.anims.set("move", chara.movement.velX, chara.movement.velY);
    else if (state instanceof IdleState) chara.anims.set("idle", chara.movement.lastVelX, chara.movement.lastVelY);
    else if (state instanceof AttackState) {
      DirAnimation attackAnim = chara.anims.get("attack");
      if (attackAnim != null) {
        attackAnim.setPlayMode(PlayMode.NORMAL);
        attackAnim.replay();
        chara.anims.set("attack", chara.movement.lastVelX, chara.movement.lastVelY);
      }
      InfuseDarknessBehaviour infuseDarknessBehaviour = chara.observers.get(InfuseDarknessBehaviour.class);
      if (infuseDarknessBehaviour != null && infuseDarknessBehaviour.isRunning()) attackEffAnim = chara.anims.get("skill1_strike");
      else attackEffAnim = chara.anims.get("attack_effect");

      AttackState attackState = (AttackState)state;
      attackEffAnim.face(chara.movement.lastVelX, chara.movement.lastVelY);
      attackEffect = new GfxAnimation(attackEffAnim.currentDir, false);
      attackEffect.rotation = MathUtils.random(0, 90);
      attackEffect.bounds.setSize(chara.bounds.width);
      attackEffect.centerTo(attackState.target);
      effectManager.start(attackEffect);
    }
  } 
  @Override
  public Observer copy() {
    return new AnimationBehaviour(effectManager);
  }
}
