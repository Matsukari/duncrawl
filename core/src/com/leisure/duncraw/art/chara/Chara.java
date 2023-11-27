package com.leisure.duncraw.art.chara;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.InteractState;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class Chara extends Art {
  public final int id;
  public Status status;
  public State state;
  public LerpMovement movement;
  public TilemapChara mapAgent;
  public Observers observers;
  private LinearAnimation<TextureRegion> animation;
  private final SpriteBatch batch;
  private static class IdGenerator {
    private static int last = 0;
    public static int gen() { 
      Logger.log("IdGenerator", "Generated id for unique resource");
      last += 1; 
      return last;
    }
  }
  public Chara(LinearAnimation<TextureRegion> frames, SpriteBatch batch) {
    id = IdGenerator.gen(); 
    this.animation = frames;
    this.batch = batch;
    movement = new LerpMovement(2f);
    observers = new Observers(this);
    setState(new IdleState());
    Status status = new Status();
    status.reset();
    setStats(status);
  }
  // This must be called after all operations are done to this chara
  public void update(float dt) {
    status.update();
    state.update(dt);
    if (state.next != null) setState(state.next); 
    // if (movement.update(dt)) 
    mapAgent.moveTo((int)bounds.x/mapAgent.getWidth(), (int)bounds.y/mapAgent.getHeight());
    movement.apply(this);
  }
  @Override
  public void render() {
    batch.draw(animation.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
  @Override
  public void moveTo(float x, float y) {
    super.moveTo(x*mapAgent.getWidth(), y*mapAgent.getHeight());
    mapAgent.moveTo((int)x, (int)y);
  }
  public void interactFront() {
    int frontX = mapAgent.x + movement.lastVelX;
    int frontY = mapAgent.map.terrainSet.rows - mapAgent.y + movement.lastVelY;
    Logger.log("Chara", String.format("Attempting to interact at %d %d", frontX, frontY));
    TilemapChara frontChara = mapAgent.map.getChara(frontX, frontY);
    Terrain terrain = mapAgent.map.terrainSet.getTerrain(frontX, frontY);
    if (frontChara != null) {
      Logger.log("Chara", "Got one");
      frontChara.chara.status.action = ActionState.INTERACT;
      observers.notifyAll(new InteractState(frontChara.chara));
    }
    else if (terrain != null) {
      Logger.log("Chara", "Interacts with terrain");
    }
  }
  public void attackFront() {}
  public void setState(State s) { state = s; state.init(this); }
  public void setStats(Status s) { status = s; }
}
