package com.leisure.duncraw.art.chara;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.IdleState;
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
    Logger.log("Chara", "Data anim front: " + data.anims.get("idle").front);
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
    int frontX = mapAgent.x + movement.lastVelX;
    int frontY = mapAgent.y + movement.lastVelY;
    Logger.log("Chara", String.format("Attempting to interact at %d %d", frontX, frontY));
    Obj belowObject = mapAgent.getObjBy(0, 0);
    Obj frontObject = mapAgent.getObjBy(movement.lastVelX, movement.lastVelY);
    TilemapChara frontChara = mapAgent.map.getChara(frontX, frontY);

    if (frontChara != null) {
      Logger.log("Chara", "Got one");
      frontChara.chara.setState(new InteractState(this));
      setState(new InteractState(frontChara.chara));
    }
    else if (belowObject != null) {
      Logger.log("Chara", "Interacts with obj below");
      belowObject.onCharaInteract(this);
    }
    else if (frontObject != null) {
      Logger.log("Chara", "Interacts with obj  ahead");
      frontObject.onCharaInteract(this);
    }
  }
  public void attackAhead() {}
  public void setStats(Status s) { status = s; }
  public void setState(State s) { 
    state = s; 
    state.init(this);  
    observers.notifyAll(state);
  }
}
