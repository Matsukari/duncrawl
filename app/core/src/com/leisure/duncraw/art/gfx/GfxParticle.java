package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GfxParticle extends Gfx { 
  public final ParticleEffect particleEffect;
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
  public void render(SpriteBatch batch) {
    particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
  }
}
