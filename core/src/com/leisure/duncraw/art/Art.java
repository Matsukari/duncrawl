package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public abstract class Art {
  public final int id;
  public Color color = new Color(1, 1, 1, 1);
  public Rectangle bounds = new Rectangle(0, 0, 0, 0);
  // protected LinearAnimation<TextureRegion> animation;
  public final SpriteBatch batch;
  public Art(SpriteBatch batch) {
    id = IdGenerator.gen();
    this.batch = batch;
  }
  // public Art(SpriteBatch batch, LinearAnimation<TextureRegion> animation) { 
  //   id = IdGenerator.gen(); 
  //   this.animation = animation;
  //   this.batch = batch;
  // }
  // public Art(SpriteBatch batch, TextureRegion... animation) {
  //   id = IdGenerator.gen();
  //   this.batch = batch;
  //   Array<TextureRegion> frames = new Array<>();
  //   for (TextureRegion frame : animation) frames.add(frame);
  //   this.animation = new LinearAnimation<>(0f, frames, PlayMode.LOOP);
  // }
  public void moveTo(float x, float y) { bounds.setPosition(x, y); }
  public void render() {
    // batch.draw(animation.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
  private static class IdGenerator {
    private static int last = 0;
    public static int gen() { 
      // Logger.log("IdGenerator", "Generated id for unique resource");
      last += 1; 
      return last;
    }
  }

}
