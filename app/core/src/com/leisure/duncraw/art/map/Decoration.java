package com.leisure.duncraw.art.map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lib.animation.LinearAnimation;

public class Decoration extends Obj {
  public Decoration(String s) { super(s); }
  public Decoration(LinearAnimation<TextureRegion> anim) { super(anim); }
  
}
