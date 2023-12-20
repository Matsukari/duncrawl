package com.leisure.duncraw.art.chara;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.LockState;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.logging.Logger;

public class Chara extends Art {
  public Status status;
  public State prevState;
  public State state;
  public boolean lockState;
  public LerpMovement movement;
  public Observers observers;
  public TilemapChara mapAgent;
  public final HashMap<String, String> sounds;  
  public final DirAnimationMap anims = new DirAnimationMap();
  public Chara(CharaData data, SpriteBatch batch) {
    super(batch);
    status = data.status;
    sounds = data.sounds;
    for (Map.Entry<String, DirAnimData> anim : data.anims.entrySet()) 
      anims.data.put(anim.getKey(), new DirAnimation(anim.getValue())); 
    anims.set("idle");
    movement = new LerpMovement(this, 2f);
    observers = new Observers(this);
    setState(new IdleState());
  }
  @Override
  public void render() {
    batch.draw(anims.current.currentDir.current(), bounds.x, bounds.y, bounds.width, bounds.height);  
  }
  // This must be called after all operations are done to this chara
  public void update(float dt) {
    status.update(dt);
    state.update(dt);
    observers.updateAll();
    if (state.next != null) setState(state.next); 
    if (movement.update(dt)) {
      setState(new IdleState());
    }
  }
  // Move the chara, attack, interact, whatever state in parallel with an observer can receive the state
  public void setState(State s, boolean force) { 
    if (lockState && !force) return;
    //   Logger.log("set state", "NOTIFIED AGAIN");
    // }
    Logger.log("Chara chaneg state", "Chnaged");
    prevState = state;
    state = s; 
    state.init(this);  
    observers.notifyAll(state);
  }
  public void setState(State s) { setState(s, false); }
  public void onDeath() {}
}
