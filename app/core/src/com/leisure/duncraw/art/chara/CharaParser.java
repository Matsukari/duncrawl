package com.leisure.duncraw.art.chara;

import java.beans.DesignMode;

import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.screen.GameScreen.Context;

public class CharaParser {
  private final Floor floor;
  private Context context;
  public CharaParser(Floor floor, Context context) {
    this.floor = floor;
    this.context = context;
  }
  public Chara from(String type, String datFile) {
    Logger.log("CharaParser", "from " + type);
    if (type.contains("Npc")) return new Npc(datFile, context.hudManager.dialogueHud);
    else if (type.contains("Enemy")) return new Enemy(Deserializer.safeLoad(CharaData.class, datFile));
    try {
      return (Chara)Class.forName("com.leisure.duncraw.art.chara."+type).getDeclaredConstructor(String.class).newInstance(datFile);
    } catch (Exception e) { 
      Logger.error(e); 
    }
    return null; 
  }
}
