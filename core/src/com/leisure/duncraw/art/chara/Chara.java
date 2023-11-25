package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.moves.LerpMovement;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class Chara extends Art {
  public final int id;
  public Status status;
  public State state;
  public LerpMovement movement;
  public TilemapChara mapAgent;
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
    setState(new IdleState());
    Status status = new Status();
    status.reset();
    setStats(status);
  }
  public void update(float dt) {
    if (movement.update(dt) && mapAgent != null) mapAgent.moveBy(movement.velX, movement.velY);
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
  public void setState(State s) { state = s; state.init(status); }
  public void setStats(Status s) { status = s; }
}
