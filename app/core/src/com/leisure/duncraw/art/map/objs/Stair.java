package com.leisure.duncraw.art.map.objs;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.map.Decoration;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.Dat;
import com.leisure.duncraw.data.ObjData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

public class Stair extends Decoration {
  public int destFloorLevel;
  public Floor floor;
  public StairData stairData;
  public static class StairData extends ObjData {
    public boolean up;
    @Override
    public void reset() {
      super.reset();
      up = true;
    }
  }
  public Stair(String datFile, Floor floor) { 
    super(datFile);
    this.floor = floor;
    if (stairData.up) destFloorLevel = floor.generator.data.level + 1;
    else destFloorLevel = floor.generator.data.level - 1;
  }
  public Stair(Terrain terrain, Floor floor, boolean up) { 
    super(terrain.anim); 
    this.floor = floor;
    if (up) destFloorLevel = floor.generator.data.level + 1;
    else destFloorLevel = floor.generator.data.level - 1;
    Logger.log("Stair", "Got stair from" + Integer.toString(floor.generator.data.level));
  }
  @Override
  public void load(String datFile) {
    stairData = loadType(datFile, StairData.class);
  }
  @Override
  public void onCharaOccupy(Chara chara) {
    if (floor != null && chara instanceof Player) {
      floor.nextLevel = destFloorLevel;
    } 
  }
  
}
