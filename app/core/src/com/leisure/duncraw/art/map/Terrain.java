package com.leisure.duncraw.art.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;

import lib.animation.LinearAnimation;

public class Terrain extends Obj {
  // A snigle block (terrain) can contain mulitple objs (usuallyloots)
  // public final ArrayList<Obj> objs = new ArrayList<>();
  // public LinearAnimation<TextureRegion> anim;
  public boolean canTravel = true;
  public String type = "none";
  public Terrain(LinearAnimation<TextureRegion> anim) {
    super(anim);
    // this.anim = anim;
  }
  public boolean traversable() {
    return canTravel;
  }
  public void setTravel(boolean v) {
    canTravel = v;
  }
  public Terrain clone() {
    Terrain terrain = new Terrain(anim);
    // terrain.objs.addAll(objs);
    terrain.canTravel = canTravel;
    terrain.type = new String(type);
    return terrain;
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
}
