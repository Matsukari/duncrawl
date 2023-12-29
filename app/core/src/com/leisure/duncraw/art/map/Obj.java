package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ObjData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Obj extends Art {
  public transient ObjData dat;
  public transient String datFile;
  public transient LinearAnimation<TextureRegion> idle;
  public transient LinearAnimation<TextureRegion> anim;
  // public Pointi tileOccupy = new Pointi(1, 1);

  public Obj(String datFile) {
    load(datFile);
  }
  public Obj(LinearAnimation<TextureRegion> anim) {
    this.anim = anim;
    idle = anim;
  }
  public void load(String datFile) {
    this.datFile = datFile;
    ObjData data = new ObjData(); 
    data.reset();
    try { data = Deserializer.load(ObjData.class, Gdx.files.local(datFile)); } catch(Exception e) { Serializer.save(data, Gdx.files.local(datFile)); }
    idle = GeneralAnimation.line(data.anims.get("idle"), PlayMode.LOOP, Math.max(data.size.x, data.size.y) * 16);
    dat = data;
    anim = idle;
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
  public void update(float dt) {}
  public void onCharaOccupy(Chara chara) {}
  public void onCharaInteract(Chara chara) {}
}
