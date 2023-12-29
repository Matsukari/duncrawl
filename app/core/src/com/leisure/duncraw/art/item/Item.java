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
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class Item extends Obj {
  public transient boolean isDrop = true;
  public int quantity;
  public int maxQuantity;
  // public String datFile;
  public transient Player owner;
  public transient ItemData itemData;
  public transient LinearAnimation<TextureRegion> dropAnim; 
  public transient LinearAnimation<TextureRegion> storeAnim;
  public Item(String datFile) {
    super(datFile);
    anim = idle;
    maxQuantity = 99;
    quantity = 0;
    // animation = storeAnim;
  }
  public Item() { super((LinearAnimation<TextureRegion>)null); }
  @Override
  public void load(String datFile) {
    this.datFile = datFile;
    itemData = new ItemData();
    itemData.reset();
    try { itemData = Deserializer.load(ItemData.class, Gdx.files.local(datFile)); } catch(Exception e) { Serializer.save(itemData, Gdx.files.local(datFile)); };
    dat = itemData;
    idle = GeneralAnimation.line(dat.anims.get("drop"));
    storeAnim = GeneralAnimation.line(dat.anims.get("store"));
  }
  @Override
  public void onCharaOccupy(Chara chara) {
    if (chara instanceof Player && isDrop) {
      Player player = (Player)chara;
      player.inventory.put(this);
      if (player.itemSel == null) player.equip(this);
    }
  }
  public Item clone() {
    Item item = new Item(datFile);
    item.isDrop = isDrop;
    item.maxQuantity = maxQuantity;
    item.quantity = quantity;
    item.owner = owner;
    return item;
  }
  public boolean use() {
    quantity--;
    if (quantity >= 0) onUse();
    else return false;
    return true;
  }
  protected void onUse() {
    Logger.log("Item", "use base class");    
  }
  @Override
  public boolean equals(Object obj) {
    return datFile.equals(((Item)obj).datFile);
  }
  // Will be called by Terrain
  @Override
  public void render(SpriteBatch batch) {
    if (isDrop) super.render(batch);
    // Logger.log("Item", "Rendered at: " + bounds.toString());
  } 
  // Will be called by the UI
  public void renderStore(SpriteBatch batch, float x, float y, float w, float h) { 
    batch.draw(storeAnim.current(), x, y, w, h); 
  }
}
