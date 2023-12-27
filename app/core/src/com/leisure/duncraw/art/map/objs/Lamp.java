package com.leisure.duncraw.art.map.objs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.gfx.GfxParticle;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.lighting.PointLight;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.manager.EffectManager;

public class Lamp extends Obj {
  private GfxParticle fireParticle;
  private final EffectManager effectManager;
  private final LightEnvironment lightEnvironment;
  private PointLight light;
  public Lamp(String datFile, LightEnvironment lightEnvironment, EffectManager effectManager) {
    super(datFile);
    this.lightEnvironment = lightEnvironment;
    this.effectManager = effectManager;
    light = new PointLight(Graphics.getSafeTextureRegion(dat.anims.get("light")));
    fireParticle = new GfxParticle(Graphics.getSafeParticle(dat.anims.get("fire")));
    fireParticle.loop = true;
    light.tint = new Color(1f, 0.2f, 0.2f, 0.6f);
    light.bounds.setSize(400, 400);
    fireParticle.particleEffect.setEmittersCleanUpBlendFunction(false);
    effectManager.start(fireParticle);
    lightEnvironment.addLight(light);
  }
  @Override
  public void render(SpriteBatch batch) {
    fireParticle.particleEffect.setPosition(bounds.x + bounds.width/2, bounds.y + bounds.height);
    light.centerTo(bounds);
    super.render(batch);
  }
}
