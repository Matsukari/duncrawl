package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import lib.animation.LinearAnimation;

public class GfxAnimation extends Gfx {
  public LinearAnimation<TextureRegion> anim;
  public boolean loop = false;
  public GfxAnimation(SpriteBatch batch, LinearAnimation<TextureRegion> anim, boolean loop) {
    super(batch);
    this.anim = anim;
    setLoop(loop);
  }
  public GfxAnimation(SpriteBatch batch) {
    super(batch);
  }
  public void setLoop(boolean val) {
    loop = val;
    if (!loop) anim.data.setPlayMode(PlayMode.NORMAL);
    else anim.data.setPlayMode(PlayMode.LOOP);
  }
  @Override
  public void start() {
    anim.reset();
  }
  @Override
  public boolean isFinished() {
    if (loop) return false;
    return anim.isFinished();
  }
  @Override
  public void render() {
    if (anim != null) batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
}
