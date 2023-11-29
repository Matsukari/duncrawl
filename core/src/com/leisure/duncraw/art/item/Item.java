package com.leisure.duncraw.art.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ItemData;
import com.leisure.duncraw.data.ObjData;
import com.leisure.duncraw.data.Serializer;

import lib.animation.LinearAnimation;

public class Item extends Obj {
  public boolean isDrop = true;
  public LinearAnimation<TextureRegion> dropAnim; 
  public LinearAnimation<TextureRegion> storeAnim;
  public Item(SpriteBatch batch, String datFile, LinearAnimation<TextureRegion> dropAnimation) {
    super(batch, datFile);
    assert dropAnimation != null;
    idle = dropAnimation;
    animation = dropAnimation;
    storeAnim = GeneralAnimation.line(dat.anims.get("store"));
    // animation = storeAnim;
  }
  @Override
  public void load(String datFile) {
    ItemData itemData = new ItemData();
    itemData.reset();
    itemData.passTo(dat);
    try { dat = Deserializer.load(ObjData.class, Gdx.files.local(datFile)); } catch(Exception e) { Serializer.save(dat, Gdx.files.local(datFile)); };
  }
  // Will be called by Terrain
  @Override
  public void render() {
    if (isDrop) super.render();
  } 
  // Will be called by the UI
  public void renderStore(int x, int y, int w, int h) {
    batch.draw(storeAnim.current(), x, y, w, h); 
  }
  @Override
  public void onCharaOccupy(Chara chara) {
    if (chara instanceof Player && isDrop) ((Player)chara).inventory.put(this);
  }
}
