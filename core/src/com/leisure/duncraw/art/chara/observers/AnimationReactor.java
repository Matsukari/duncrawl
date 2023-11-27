package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.DirAnimation;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.MoveState;

public class AnimationReactor extends Observer {
  @Override
  public void invoke(State state) {
    if (state instanceof MoveState) {
      DirAnimation anim = chara.anims.get("move");
      if (anim != null) chara.setAnimation("move");
 
    }
  } 
}
