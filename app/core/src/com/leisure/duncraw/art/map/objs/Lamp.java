package com.leisure.duncraw.art.map.objs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.art.gfx.GfxParticle;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.lighting.PointLight;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.Floor;

public class Lamp extends Obj {
  private GfxAnimation fireAnim;
  private final EffectManager effectManager;
  private final LightEnvironment lightEnvironment;
  private PointLight light;
  public Lamp(String datFile, LightEnvironment lightEnvironment, EffectManager effectManager) {
    super(datFile);
    this.lightEnvironment = lightEnvironment;
    this.effectManager = effectManager;
    light = new PointLight(Graphics.getSafeTextureRegion(dat.anims.get("light")));
    fireAnim = new GfxAnimation(GeneralAnimation.line(dat.anims.get("fire")), true);
    light.bounds.setSize(400, 400);
    light.tint = new Color(1f, 0.2f, 0.2f, 0.5f);
    effectManager.start(fireAnim);
    lightEnvironment.addLight(light);
  }
  @Override
  public void onUnstage(Floor floor) {
    effectManager.stop(fireAnim);
  }
  @Override
  public void render(SpriteBatch batch) {
    fireAnim.bounds.setSize(bounds.width, bounds.height);
    fireAnim.bounds.setPosition(bounds.x, bounds.y + bounds.height / 2);
    light.centerTo(this);
    super.render(batch);
  }
}
