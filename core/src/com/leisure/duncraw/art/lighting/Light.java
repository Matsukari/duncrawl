package com.leisure.duncraw.art.lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.map.Obj;

import lib.animation.LinearAnimation;

public class Light extends Obj {  
  public Light(SpriteBatch batch, LinearAnimation<TextureRegion> anim) {
    super(batch, anim);
  }
}
