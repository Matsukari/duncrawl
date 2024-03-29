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
    // if (chara.prevState instanceof MoveState) return;
    int frontX = chara.mapAgent.x + chara.movement.lastVelX, frontY = chara.mapAgent.y + chara.movement.lastVelY;
    Logger.log("Chara", String.format("Attempting to interact at %d %d", frontX, frontY));
    if (tryInteract(chara.mapAgent.getObjBy(0, 0), true)) { }
    else if (tryInteract(chara.mapAgent.getObjBy(chara.movement.lastVelX, chara.movement.lastVelY), false)) {}
    else if (tryInteract(chara.mapAgent.map.getChara(frontX, frontY), false)) {}
    else {
      attack(null);
    }
  } 
  private <T> boolean tryInteract(T other, boolean below) {
    if (other instanceof Obj) {
      // Logger.log("InteractState", "At obj");
      chara.setState(new InteractObjState((Obj)other));
      if (below) ((Obj)other).onCharaOccupy(chara);
      else ((Obj)other).onCharaInteract(chara);
      return true;
    }
    else if (other instanceof TilemapChara) {
      // Logger.log("InteractState", "At chara");
      TilemapChara otherAgent = ((TilemapChara)other);
      if (otherAgent.chara instanceof Enemy) attack(other);
      else if (otherAgent.chara instanceof Npc) {
        chara.lockState = true;
        chara.setState(new TalkState(otherAgent.chara), true);
        otherAgent.chara.setState(new TalkState(chara), true);
      }
      return true;
    }
    return false;
  }
  private <T> void attack(T other) {
    Chara target = null;
    if (other != null && (other instanceof TilemapChara)) {
      TilemapChara otherAgent = ((TilemapChara)other);
      if (otherAgent.chara instanceof Enemy) target = ((TilemapChara)other).chara;
      // if (target != null) Logger.log("InteractState", "OK");
      // else Logger.log("InteractState", "null");
    }
    if (chara.prevState instanceof DashState) chara.setState(new DashAttackState(target), true);
    else chara.setState(new AttackState(target));
  }
  @Override
  public void update(float dt) {

  }
  
}
