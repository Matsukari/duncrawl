package com.leisure.duncraw.manager;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.gfx.Gfx;

public class EffectManager {
  public SpriteBatch batch = new SpriteBatch();
  public ArrayList<Gfx> effects = new ArrayList<>();
  public EffectManager() {
  }
  public void start(Gfx gfx) { 
    if (effects.contains(gfx)) return;
    effects.add(gfx); 
    gfx.start();
  }
  public void updateAll(float dt) {
    for (int i = 0; i < effects.size(); i++) {
      effects.get(i).update(dt);
      if (effects.get(i).isFinished()) effects.remove(effects.get(i));
    } 
  }
  public void renderAll(Camera camera) {
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for (Gfx effect : effects) effect.render(); 
    batch.end();
  }
}
