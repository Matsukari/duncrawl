package com.leisure.duncraw.art.chara;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.InteractState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class Chara extends Art {
  public State state;
  public LerpMovement movement;
  public TilemapChara mapAgent;
  public Observers observers;
  public Status status = new Status();
  public final HashMap<String, DirAnimation> anims = new HashMap<>();
  public DirAnimation animAgent;
  public Chara(LinearAnimation<TextureRegion> frames, SpriteBatch batch) {
    super(batch, frames);
  }
  public Chara(CharaData data, SpriteBatch batch) {
    super(batch);
    status = data.status;
    Logger.log("Chara", "Data anim front: " + data.anims.get("idle").front);
    for (Map.Entry<String, DirAnimData> anim : data.anims.entrySet()) {
      anims.put(anim.getKey(), new DirAnimation(anim.getValue()));
    }
    animAgent = anims.get("idle");
    animation = animAgent.anims[DirAnimation.FRONT];
  }
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
    if (movement.update(dt)) mapAgent.moveBy(movement.lastVelX, movement.lastVelY);
    if (animAgent != null) {
      Logger.log("Chara", "Change animation");
      if (movement.lastVelX != 0) animation = animAgent.getSideFlip(movement.lastVelX);
      else if (movement.lastVelY < 0) animation = animAgent.anims[DirAnimation.FRONT];
      else if (movement.lastVelY > 0) animation = animAgent.anims[DirAnimation.BACK];
    }
    movement.apply(this);
  }
  @Override
  public void moveTo(float x, float y) {
    super.moveTo(x*mapAgent.getWidth(), y*mapAgent.getHeight());
    mapAgent.moveTo((int)x, (int)y);
    setState(new MoveState());
  }
  public void interactAhead() {
    int frontX = mapAgent.x + movement.lastVelX;
    int frontY = mapAgent.y + movement.lastVelY;
    Logger.log("Chara", String.format("Attempting to interact at %d %d", frontX, frontY));
    TilemapChara frontChara = mapAgent.map.getChara(frontX, frontY);
    Obj belowObject = mapAgent.getObjBy(0, 0);
    Obj frontObject = mapAgent.getObjBy(movement.lastVelX, movement.lastVelY);

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
  public void attackFront() {}
  public void setAnimation(String a) {
    Logger.log("Chara", "Set animation to " + a);
    if (anims.get(a) != null) animAgent = anims.get(a);
  }
  public void setStats(Status s) { status = s; }
  public void setState(State s) { 
    state = s; 
    state.init(this);  
    observers.notifyAll(state);
  }
}
