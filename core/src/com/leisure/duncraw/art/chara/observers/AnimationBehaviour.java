package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
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
  private Gfx attackEffect;
  private DirAnimation attackAnim;
  public AnimationBehaviour(EffectManager effectManager) { this.effectManager = effectManager; }
  @Override
  public void init(Chara c) {
    super.init(c);
    attackAnim = chara.anims.get("attack");
  }
  @Override
  public void invoke(State state) {
    Logger.log("AnimationBehaviour", "Invoke");
    if (state instanceof MoveState) chara.anims.set("move");
    else if (state instanceof DashState) chara.anims.set("idle");
    else if (state instanceof IdleState) chara.anims.set("idle", chara.movement.lastVelX, chara.movement.lastVelY);
    else if (state instanceof AttackState && attackAnim != null) {
      attackAnim.face(chara.movement.lastVelX, chara.movement.lastVelY);
      attackEffect = new GfxAnimation(effectManager.batch, attackAnim.currentDir);
      attackEffect.bounds.setPosition(chara.bounds.x, chara.bounds.y);
      attackEffect.bounds.setSize(chara.bounds.width);
      effectManager.start(attackEffect);
    }
  } 
  @Override
  public Observer copy() {
    return new AnimationBehaviour(effectManager);
  }
}
