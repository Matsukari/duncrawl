package com.leisure.duncraw.art.map.objs;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.data.objs.ChestDat;

import lib.animation.LinearAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Chest extends Obj {
  public ChestDat dat = new ChestDat();
  private LinearAnimation<TextureRegion> idle;
  private LinearAnimation<TextureRegion> open;
  public Chest(String datFile, SpriteBatch batch) {
    super(null, batch);
    dat.reset();
    try { Deserializer.load(ChestDat.class, Gdx.files.local(datFile)); } catch(Exception e) { Serializer.save(dat, Gdx.files.local(datFile)); }
    idle = GeneralAnimation.line(dat.idle, PlayMode.NORMAL);
    open = GeneralAnimation.line(dat.openAnim, PlayMode.NORMAL);
    animation = idle;
  }
  @Override
  public void update(float dt) {
  }
  @Override
  public void onCharaInteract(Chara chara) {
    animation = open;
    animation.reset();
  }
}
