package com.leisure.duncraw.manager;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.Effect;
import com.leisure.duncraw.art.gfx.Gfx;

public class EffectManager {
  public SpriteBatch batch = new SpriteBatch();
  public ArrayList<Effect> effects = new ArrayList<>();
  public ArrayList<Gfx> renderEffects =new ArrayList<>();
  public EffectManager() {
  }
  public void start(Effect eff) { 
    if (effects.contains(eff)) return;
    if (eff instanceof Gfx) renderEffects.add((Gfx)eff);
    effects.add(eff); 
    eff.start();
  }
  public void stop(Effect eff) {
    if (!effects.contains(eff)) return;
    if (eff instanceof Gfx) renderEffects.remove((Gfx)eff);
    eff.stop();
    effects.remove(eff); 
  }
  public void updateAll(float dt) {
    for (int i = 0; i < effects.size(); i++) {
      effects.get(i).update(dt);
      if (effects.get(i).isFinished()) stop(effects.get(i));
    } 
  }
  public void renderAll(Camera camera) {
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for (Gfx effect : renderEffects) effect.render(batch); 
    batch.end();
  }
}
