package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;

import lib.animation.LinearAnimation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Obj extends Art {
  public Obj(LinearAnimation<TextureRegion> frames, SpriteBatch batch) {
    super(batch, frames);
  }
  public Obj(SpriteBatch batch, TextureRegion... animation) { super(batch, animation); }
  public void onCharaOccupy(Chara chara) {}
  public void onCharaInteract(Chara chara) {}
}
