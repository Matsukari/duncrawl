package com.leisure.duncraw.art.map.objs;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ItemData;
import com.leisure.duncraw.data.ObjData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Chest extends Obj {
  public Item containedItem;
  private LinearAnimation<TextureRegion> open;
  ChestData chestData;
  public static class ChestData extends ItemData {
    public String containedItemType; // Classname of item
    public String containedItemData; // datFile
    public boolean empty;
    @Override
    public void reset() {
      super.reset();
      containedItemData = "";
      containedItemType = "";
      empty = false;
    }
  }
  public Chest(String datFile) {
    super(datFile);
  }
  @Override
  public void load(String datFile) {
    chestData = loadType(datFile, ChestData.class);
    dat = chestData;
    try {
      containedItem = (Item)Class.forName(chestData.containedItemType).getDeclaredConstructor(String.class).newInstance(chestData.containedItemData);
    } catch (Exception e) { Logger.log("Chest", "Empty"); }
    open = GeneralAnimation.line(dat.anims.get("open"), PlayMode.NORMAL);
  }
  @Override
  public void onCharaInteract(Chara chara) {
    if (chestData.empty) return;
    anim = open;
    anim.reset();
    if (containedItem != null) {
      containedItem.onCharaOccupy(chara); 
      chestData.empty = true;
      Serializer.save(chestData, datFile);
    }
  }
}
