package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.DashState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.EffectManager;

public class DashBehaviour extends Observer {
  private final EffectManager effectManager;
  public DashBehaviour(EffectManager effectManager) {
    this.effectManager = effectManager;
  }
  @Override
  public void invoke(State state) {
    if (state instanceof DashState) {
      animate("teleport_from");
      int step = 2;
      if (chara.movement.isMoving()) step = 3;
      Logger.log("DashBehaviour", String.format("Dash: %d %d", chara.movement.lastVelX, chara.movement.lastVelY));
      chara.setState(new MoveState(chara.mapAgent.x + chara.movement.lastVelX * step, chara.mapAgent.y + chara.movement.lastVelY * step, false)); 
      chara.anims.set("idle", chara.movement.lastVelX, chara.movement.lastVelY);
      chara.movement.stop();
      chara.movement.reset();
      animate("teleport_to");
    }
  }
  public void animate(String anim) {
    DirAnimation effAnim = chara.anims.get(anim);
    if (effAnim == null) return;
    effAnim.face(chara.movement.lastVelX, chara.movement.lastVelY);
    GfxAnimation effect = new GfxAnimation(effAnim.currentDir, false);
    effect.bounds.setPosition(chara.bounds.x, chara.bounds.y);
    effect.bounds.setSize(chara.bounds.width);
    effectManager.start(effect);
  }
  @Override
  public Observer copy() {
    return new DashBehaviour(effectManager);
  }
}
