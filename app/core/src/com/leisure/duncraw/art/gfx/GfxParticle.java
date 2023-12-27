package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GfxParticle extends Gfx { 
  public final ParticleEffect particleEffect;
  public float delta = 0;
  public GfxParticle(ParticleEffect particleEffect) {
    this.particleEffect = particleEffect;
  }
  @Override
  public void start() {
    particleEffect.start();
  } 
  @Override
  public boolean isFinished() {
    if (loop) return false;
    return particleEffect.isComplete();
  }
  @Override
  public void update(float dt) {
  }
  @Override
  public void render(SpriteBatch batch) {
    particleEffect.update(Gdx.graphics.getDeltaTime());
    particleEffect.setPosition(bounds.x, bounds.y);
    particleEffect.draw(batch);
    // delta += Gdx.graphics.getDeltaTime();
  }
}
