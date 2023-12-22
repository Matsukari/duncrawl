package com.leisure.duncraw.art.item;

import com.leisure.duncraw.logging.Logger;

public class ItemParser {
  public static Item from(String str, String datFile) {
    Logger.log("ItemParser", "from " + str);
    if (str.contains("magic_dust")) return new Item(datFile); 
    return null;
  }
}
