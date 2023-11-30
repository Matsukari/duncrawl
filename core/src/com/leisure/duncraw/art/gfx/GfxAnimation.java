package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import lib.animation.LinearAnimation;

public class GfxAnimation extends Gfx {
  public LinearAnimation<TextureRegion> anim;
  public GfxAnimation(SpriteBatch batch, LinearAnimation<TextureRegion> anim) {
    super(batch);
    this.anim = anim;
    anim.data.setPlayMode(PlayMode.NORMAL);
  }
  @Override
  public void start() {
    anim.reset();
  }
  @Override
  public boolean isFinished() {
    return anim.isFinished();
  }
  @Override
  public void render() {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
}
