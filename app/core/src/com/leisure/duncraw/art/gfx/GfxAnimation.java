package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import lib.animation.LinearAnimation;

public class GfxAnimation extends Gfx {
  public LinearAnimation<TextureRegion> anim;
  public float rotation = 0;
  public boolean forceEnd = false;
  public GfxAnimation(LinearAnimation<TextureRegion> anim, boolean loop) {
    this.anim = anim;
    setLoop(loop);
  }
  public void setLoop(boolean val) {
    if (anim == null) return;
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
    if (forceEnd) return true;
    if (loop) return false;
    return anim.isFinished();
  }
  @Override
  public void render(SpriteBatch batch) {
    if (anim != null) batch.draw(anim.current(), bounds.x, bounds.y, bounds.width/2, bounds.height/2, bounds.width, bounds.height, 1f, 1f, rotation);
  }
}
