package com.leisure.duncraw.art;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.leisure.duncraw.data.GeneralAnimation;

import lib.animation.LinearAnimation;

public class AnimatedArt extends Art {
  public LinearAnimation<TextureRegion> anim;
  public AnimatedArt(LinearAnimation<TextureRegion> anim) {
    this.anim = anim;
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.setColor(tint);
    batch.draw(anim.current(), bounds.x,  bounds.y, bounds.width, bounds.height);
    batch.setColor(Color.WHITE);
  }
  
}
