package com.leisure.duncraw.art.chara;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;

import lib.math.Pointi;

public class Chara extends Art {
  public Status status;
  public State prevState;
  public State state;
  public boolean lockState;
  public LerpMovement movement;
  public Observers observers;
  public TilemapChara mapAgent;
  public Vector2 offset = new Vector2();
  public final HashMap<String, String> sounds;     
  public final DirAnimationMap anims = new DirAnimationMap();
  public Chara(CharaData data) {
    status = data.status;
    sounds = data.sounds;
    offset.x = data.offsetX;
    offset.y = data.offsetY;
    for (Map.Entry<String, DirAnimData> anim : data.anims.entrySet()) 
      anims.data.put(anim.getKey(), new DirAnimation(anim.getValue())); 
    anims.set("idle");
    movement = new LerpMovement(this, 2f);
    observers = new Observers(this);
    setState(new IdleState());
  }
  @Override
  public float getWorldX() { return bounds.x + offset.x; }
  @Override
  public float getWorldY() { return bounds.y + offset.y; }
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(anims.current.currentDir.current(), bounds.x + offset.x, bounds.y + offset.y, bounds.width, bounds.height);  
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
    prevState = state;
    state = s; 
    state.init(this);  
    observers.notifyAll(state);
  }
  public void setState(State s) { setState(s, false); }
  public void onDeath() {}
}
