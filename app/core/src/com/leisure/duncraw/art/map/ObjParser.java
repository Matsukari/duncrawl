package com.leisure.duncraw.art.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.map.objs.Chest;
import com.leisure.duncraw.logging.Logger;

public class ObjParser {
  public static Obj from(String str, String datFile) {
    Logger.log("ObjParser", "from " + str);
    if (str.contains("chest_box")) return new Chest(datFile); 
    else Logger.log("ObjParser", "Cannot parse");
    return null;
  }
}
