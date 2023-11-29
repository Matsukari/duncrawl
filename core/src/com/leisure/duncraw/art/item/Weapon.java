package com.leisure.duncraw.art.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lib.animation.LinearAnimation;

public class Weapon extends Item { 
  public Weapon(SpriteBatch batch, String datFile, LinearAnimation<TextureRegion> dropAnimation) {
    super(batch, datFile, dropAnimation);
  }
}
