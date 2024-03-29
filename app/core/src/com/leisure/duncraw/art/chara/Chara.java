package com.leisure.duncraw.art.chara;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.DeathState;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.CharaManager;

public class Chara extends Art {
  private CharaManager manager;
  public Status status;
  public State prevState;
  public State state;
  public boolean lockState;
  public LerpMovement movement;
  public Observers observers;
  public TilemapChara mapAgent;
  public Vector2 offset = new Vector2();
  public HashMap<String, String> sounds;     
  public DirAnimationMap anims = new DirAnimationMap();
  public CharaData dat;
  public String datFile;
  public Obj dropObj;
  public Chara(CharaData data) {
    init(data);
  }
  protected Chara() {
  }
  protected void init(CharaData data) {
    dat = data;
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
  public void render(SpriteBatch batch) {
    batch.setColor(tint);
    batch.draw(anims.current.currentDir.current(), bounds.x + offset.x, bounds.y + offset.y, bounds.width, bounds.height);  
    batch.setColor(Color.WHITE);
  }
  // This must be called after all operations are done to this chara
  public void update(float dt) {
    // if (status.dead) return; 
    status.update(dt);
    state.update(dt);
    observers.updateAll();
    if (state.next != null) setState(state.next); 
    if (anims.current.currentDir.isFinished()) anims.set("idle", movement.lastVelX, movement.lastVelY);
    if (movement.update(dt)) {
      setState(new IdleState());
      // Logger.log("Chara", "Set to idle");
    }
  }
  // Move the chara, attack, interact, whatever state in parallel with an observer can receive the state
  public void setState(State s, boolean force) { 
    if (status.dead || (lockState && !force)) return;
    prevState = state;
    state = s; 
    state.init(this);  
    observers.notifyAll(state);
  }
  @Override public float getWorldX() { return bounds.x + offset.x; }
  @Override public float getWorldY() { return bounds.y + offset.y; }
  public void setManager(CharaManager manager) { this.manager = manager; }
  public void setState(State s) { setState(s, false); }
  public void kill() { 
    // if (manager != null) {
    //   manager.kill(this); 
    // }
    onDeath();
    status.dead = true;
  }
  public void onDeath() {
    Logger.log("Chara", "onDeath");
    status.dead = false;
    setState(new DeathState(), true);
    status.dead = true;
    if (dropObj != null) {
      mapAgent.map.background.putObject(dropObj, mapAgent.x, mapAgent.y);
    }
  }
}
