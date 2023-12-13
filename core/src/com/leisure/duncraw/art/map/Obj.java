package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ObjData;
import com.leisure.duncraw.data.Serializer;

import lib.animation.LinearAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Obj extends Art {
  public Obj next;
  public ObjData dat = new ObjData();
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
    dat.reset();
    try { dat = Deserializer.load(ObjData.class, Gdx.files.local(datFile)); } catch(Exception e) { Serializer.save(dat, Gdx.files.local(datFile)); }
    idle = GeneralAnimation.line(dat.anims.get("idle"));
    anim = idle;
  }
  public Obj getTail() {
    Obj node = this;
    while (node.next != null) node = node.next;
    return node;
  }
  @Override
  public void render() {
    batch.draw(anim.current(), bounds.x, bounds.y, bounds.width, bounds.height);
    // for (Terrain node = next; node != null; node = node.next) node.render();
    if (next != null) next.render();
  }
  public void update(float dt) {}
  public void onCharaOccupy(Chara chara) {}
  public void onCharaInteract(Chara chara) {}
}
