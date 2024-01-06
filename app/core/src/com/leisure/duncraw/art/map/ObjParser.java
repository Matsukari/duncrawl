package com.leisure.duncraw.art.map;

import com.leisure.duncraw.art.map.objs.BigDoor;
import com.leisure.duncraw.art.map.objs.Lamp;
import com.leisure.duncraw.art.map.objs.Stair;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

public class ObjParser {
  private final Floor floor;
  public ObjParser(Floor floor) {
    this.floor = floor;
  }
  public Obj from(String type, String datFile) {
    Logger.log("ObjParser", "from " + type);
    if (type.contains("Lamp")) return new Lamp(datFile, floor.lightEnvironment, floor.effectManager);
    else if (type.contains("BigDoor")) return new BigDoor(datFile, floor);
    else if (type.contains("Stair")) return new Stair(datFile, floor);
    try {
      return (Obj)Class.forName("com.leisure.duncraw.art.map."+type).getDeclaredConstructor(String.class).newInstance(datFile);
    } catch (Exception e) { 
      try {
        return (Obj)Class.forName("com.leisure.duncraw.art.map.objs."+type).getDeclaredConstructor(String.class).newInstance(datFile);
      } catch (Exception e2) { Logger.error(e); }
    }
    return null; 
  }
}
