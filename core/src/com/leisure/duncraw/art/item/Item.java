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
import com.leisure.duncraw.data.Serializer;

import lib.animation.LinearAnimation;

public class Item extends Obj {
  public boolean isDrop = true;
  public Player owner;
  public ItemData itemData;
  public LinearAnimation<TextureRegion> dropAnim; 
  public LinearAnimation<TextureRegion> storeAnim;
  public Item(SpriteBatch batch, String datFile) {
    super(batch, datFile);
    idle = GeneralAnimation.line(dat.anims.get("drop"));
    storeAnim = GeneralAnimation.line(dat.anims.get("store"));
    anim = idle;
    // animation = storeAnim;
  }
  @Override
  public void load(String datFile) {
    itemData = new ItemData();
    itemData.reset();
    try { itemData = Deserializer.load(ItemData.class, Gdx.files.local(datFile)); } catch(Exception e) { Serializer.save(itemData, Gdx.files.local(datFile)); };
    dat = itemData;
  }
  @Override
  public void onCharaOccupy(Chara chara) {
    if (chara instanceof Player && isDrop) {
      Player player = (Player)chara;
      player.inventory.put(this);
      owner = player;
      if (player.itemSel == null) player.equip(this);
    }
  }
  public void use() {
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
}
