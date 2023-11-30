package com.leisure.duncraw.art.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;

import lib.animation.LinearAnimation;

public class Gfx extends Art {
  public Gfx(SpriteBatch batch, LinearAnimation<TextureRegion> anim) {
    super(batch);
  } 
}
