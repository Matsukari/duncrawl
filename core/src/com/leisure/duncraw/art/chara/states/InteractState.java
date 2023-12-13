package com.leisure.duncraw.art.chara.states;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.Npc;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.logging.Logger;

// Still don't know who the chara is interacting with
public class InteractState extends State {
  @Override
  public void init(Chara s) {
    super.init(s);
    interactAhead();
  }
  public void interactAhead() { 
    int frontX = chara.mapAgent.x + chara.movement.lastVelX, frontY = chara.mapAgent.y + chara.movement.lastVelY;
    Logger.log("Chara", String.format("Attempting to interact at %d %d", frontX, frontY));
    if (tryInteract(chara.mapAgent.getObjBy(0, 0), true)) { }
    else if (tryInteract(chara.mapAgent.getObjBy(chara.movement.lastVelX, chara.movement.lastVelY), false)) {}
    else if (tryInteract(chara.mapAgent.map.getChara(frontX, frontY), false)) {}
  } 
  private <T> boolean tryInteract(T other, boolean below) {
    if (other instanceof Obj) {
      chara.setState(new InteractObjState((Obj)other));
      if (below) ((Obj)other).onCharaOccupy(chara);
      else ((Obj)other).onCharaInteract(chara);
      return true;
    }
    else if (other instanceof TilemapChara) {
      TilemapChara otherAgent = ((TilemapChara)other);
      if (otherAgent.chara instanceof Enemy) chara.setState(new AttackState(((TilemapChara)other).chara));
      else if (otherAgent.chara instanceof Npc) {
        chara.setState(new TalkState(otherAgent.chara));
        otherAgent.chara.setState(new TalkState(chara));
      }
      return true;
    }
    return false;
  }
  @Override
  public void update(float dt) {

  }
  
}
