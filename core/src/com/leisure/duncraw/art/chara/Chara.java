package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.map.TilemapChara;

import lib.animation.LinearAnimation;

public class Chara extends Art {
  public final int id;
  public Status status;
  public Movement movement;
  public TilemapChara mapAgent;
  private LinearAnimation<TextureRegion> animation;
  private final SpriteBatch batch;
  private static class IdGenerator {
    private static int last = 0;
    public static int gen() { 
      last += 1; 
      return last;
    }
  }
  public Chara(LinearAnimation<TextureRegion> frames, SpriteBatch batch) {
    id = IdGenerator.gen();
    this.animation = frames;
    this.batch = batch;
  }
  public void update(float dt) {
    bounds.x = movement.nextStepX * mapAgent.getWidth();
    bounds.y = movement.nextStepY * mapAgent.getHeight();
    mapAgent.move(movement.nextStepX, movement.nextStepY);
  }
  @Override
  public void render() {
    batch.draw(animation.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
  public void onTalked(Chara chara) {}
  public void onAttacked(Chara chara) {}
  public void attack(Chara chara) {}
}
