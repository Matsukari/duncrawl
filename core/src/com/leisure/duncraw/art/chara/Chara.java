package com.leisure.duncraw.art.chara;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.InteractObjState;
import com.leisure.duncraw.art.chara.states.InteractState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.logging.Logger;


public class Chara extends Art {
  public Status status = new Status();
  public State state;
  public LerpMovement movement;
  public Observers observers;
  public TilemapChara mapAgent;
  public final DirAnimationMap anims = new DirAnimationMap();
  // Load from .dat file
  public Chara(CharaData data, SpriteBatch batch) {
    super(batch);
    status = data.status;
    for (Map.Entry<String, DirAnimData> anim : data.anims.entrySet()) {
      // Logger.log("Chara", "Loading animation dat = " + anim.getKey());
      anims.data.put(anim.getKey(), new DirAnimation(anim.getValue()));
    }
    anims.set("idle");
  }
  // Initializer block
  {
    movement = new LerpMovement(this, 2f);
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
  }
  @Override
  public void render() {
    batch.draw(anims.current.currentDir.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
  @Override
  public void moveTo(float x, float y) {
    setState(new MoveState());
    super.moveTo(x*mapAgent.getWidth(), y*mapAgent.getHeight());
    mapAgent.moveTo((int)x, (int)y);
  }
  public void moveBy(int x, int y) {
    setState(new MoveState());
    anims.current.face(x, y);
    movement.moveBy(x, y);
  }
  public void interactAhead() {
    int frontX = mapAgent.x + movement.lastVelX, frontY = mapAgent.y + movement.lastVelY;
    Logger.log("Chara", String.format("Attempting to interact at %d %d", frontX, frontY));
    if (tryInteract(mapAgent.getObjBy(0, 0), true)) { }
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
      if (((TilemapChara)other).chara instanceof Enemy) setState(new AttackState(((TilemapChara)other).chara));
      else setState(new InteractState(((TilemapChara)other).chara));
      return true;
    }
    return false;
  }
}
