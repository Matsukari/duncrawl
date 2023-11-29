package com.leisure.duncraw.art.item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.logging.Logger;

public class ItemParser {
  public static Item from(String str, String datFile, SpriteBatch batch) {
    Logger.log("ItemParser", "from " + str);
    if (str.contains("magic_dust")) return new Item(batch, datFile, Graphics.dropAnim); 
    return null;
  }
}
