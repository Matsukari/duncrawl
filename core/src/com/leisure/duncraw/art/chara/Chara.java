package com.leisure.duncraw.art.chara;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.InteractObjState;
import com.leisure.duncraw.art.chara.states.InteractState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class Chara extends Art {
  public Status status = new Status();
  public State state;
  public LerpMovement movement;
  public Observers observers;
  public TilemapChara mapAgent;
  public final DirAnimationMap anims = new DirAnimationMap();
  public Chara(LinearAnimation<TextureRegion> frames, SpriteBatch batch) {
    super(batch, frames);
  }
  // Load from .dat file
  public Chara(CharaData data, SpriteBatch batch) {
    super(batch);
    status = data.status;
    // Logger.log("Chara", "Data anim front: " + data.anims.get("idle").front);
    for (Map.Entry<String, DirAnimData> anim : data.anims.entrySet()) 
      anims.data.put(anim.getKey(), new DirAnimation(anim.getValue())); 
    anims.set("idle");
  }
  // Initializer block
  {
    movement = new LerpMovement(2f);
    observers = new Observers(this);
    status.reset();
    setState(new IdleState());
  }
  // This must be called after all operations are done to this chara
  public void update(float dt) {
    status.update();
    state.update(dt);
    if (state.next != null) setState(state.next); 
    if (movement.update(dt)) {
      setState(new IdleState());
      mapAgent.moveBy(movement.lastVelX, movement.lastVelY);
    }
    movement.apply(this);
    animation = anims.current.currentDir;
  }
  @Override
  public void moveTo(float x, float y) {
    setState(new MoveState());
    super.moveTo(x*mapAgent.getWidth(), y*mapAgent.getHeight());
    mapAgent.moveTo((int)x, (int)y);
  }
  public void moveBy(int x, int y) {
    setState(new MoveState());
    movement.moveBy(x, y);
    anims.current.face(x, y);
  }
  public void interactAhead() {
    int frontX = mapAgent.x + movement.lastVelX, frontY = mapAgent.y + movement.lastVelY;
    Logger.log("Chara", String.format("Attempting to interact at %d %d", frontX, frontY));
    if (tryInteract(mapAgent.getObjBy(0, 0), true)) {}
    else if (tryInteract(mapAgent.getObjBy(movement.lastVelX, movement.lastVelY), false)) {}
    else if (tryInteract(mapAgent.map.getChara(frontX, frontY), false)) {}
  }
  public void setState(State s) { 
    state = s; 
    state.init(this);  
    observers.notifyAll(state);
  }
  private <T> boolean tryInteract(T other, boolean below) {
    if (other instanceof Obj) {
      setState(new InteractObjState((Obj)other));
      if (below) ((Obj)other).onCharaOccupy(this);
      else ((Obj)other).onCharaInteract(this);
      return true;
    }
    else if (other instanceof TilemapChara) {
      if (((TilemapChara)other).chara instanceof Enemy) {
        setState(new AttackState(((TilemapChara)other).chara));
        ((TilemapChara)other).chara.setState(new HurtState(this));
      } else {
        setState(new InteractState(((TilemapChara)other).chara));
        ((TilemapChara)other).chara.setState(new InteractState(this));
      }
      return true;
    }
    return false;
  }
}
