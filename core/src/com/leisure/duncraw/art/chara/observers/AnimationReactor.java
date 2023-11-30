package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.logging.Logger;

public class AnimationReactor extends Observer {
  @Override
  public void invoke(State state) {
    Logger.log("AnimationReactor", "Invoke");
    if (state instanceof MoveState) chara.anims.set("move");
    else if (state instanceof IdleState) chara.anims.set("idle", chara.movement.lastVelX, chara.movement.lastVelY);
    else if (state instanceof AttackState) chara.anims.set("attack", chara.movement.lastVelX, chara.movement.lastVelY);
  } 
}
