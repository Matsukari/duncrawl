package com.leisure.duncraw.art.chara;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;

public class Chara extends Art {
  public Status status = new Status();
  public State prevState;
  public State state;
  public LerpMovement movement;
  public Observers observers;
  public TilemapChara mapAgent;
  public final HashMap<String, String> sounds;  
  public final DirAnimationMap anims = new DirAnimationMap();
  public Chara(CharaData data, SpriteBatch batch) {
    super(batch);
    status = data.status;
    sounds = data.sounds;
    for (Map.Entry<String, DirAnimData> anim : data.anims.entrySet()) anims.data.put(anim.getKey(), new DirAnimation(anim.getValue())); 
    anims.set("idle");
  } {
    movement = new LerpMovement(this, 2f);
    observers = new Observers(this);
    status.reset();
    setState(new IdleState());
  }
  // This must be called after all operations are done to this chara
  public void update(float dt) {
    status.update();
    state.update(dt);
    observers.updateAll();
    if (state.next != null) setState(state.next); 
    if (movement.update(dt)) {
      setState(new IdleState());
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
    if (!movement.moveBy(x, y)) setState(new IdleState());
  }
  public void setState(State s) { 
    prevState = state;
    state = s; 
    state.init(this);  
    observers.notifyAll(state);
  }
}
