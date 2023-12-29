package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ObjData;

import lib.animation.LinearAnimation;

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
  public Obj() {}
  public void load(String datFile) { loadType(datFile, ObjData.class); }
  protected <T extends ObjData> T loadType(String datFile, Class<T> clazz) {
    this.datFile = datFile; 
    T typeData = Deserializer.safeLoad(clazz, datFile);
    dat = typeData;
    try { idle = GeneralAnimation.line(dat.anims.get("idle"), PlayMode.LOOP, Math.max(dat.size.x, dat.size.y) * 16); }
    catch (Exception e) {}
    anim = idle;
    return typeData;
  }
  @Override
  public void render(SpriteBatch batch) {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
  }
  public void update(float dt) {}
  public void onCharaOccupy(Chara chara) {}
  public void onCharaInteract(Chara chara) {}
}
