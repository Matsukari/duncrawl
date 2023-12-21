package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ObjData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Obj extends Art {
  public ObjData dat;
  public LinearAnimation<TextureRegion> idle;
  public LinearAnimation<TextureRegion> anim;
  public Obj(SpriteBatch batch, String datFile) {
    super(batch); load(datFile);
  }
  public Obj(SpriteBatch batch, LinearAnimation<TextureRegion> anim) {
    super(batch);
    this.anim = anim;
    idle = anim;
  }
  public void load(String datFile) {
    ObjData data = new ObjData(); 
    data.reset();
    try { data = Deserializer.load(ObjData.class, Gdx.files.local(datFile)); } catch(Exception e) { Serializer.save(data, Gdx.files.local(datFile)); }
    idle = GeneralAnimation.line(data.anims.get("idle"));
    dat = data;
    anim = idle;
  }
  @Override
  public void render() {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
    // for (Terrain node = next; node != null; node = node.next) node.render();
  }
  public void update(float dt) {}
  public void onCharaOccupy(Chara chara) {}
  public void onCharaInteract(Chara chara) {}
}
