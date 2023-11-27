package com.leisure.duncraw.data.objs;

import com.leisure.duncraw.data.Dat;

public class ChestDat extends Dat {
  public String idle;
  public String openAnim;
  public String openSfx;
  @Override
  public void reset() {
    idle = "images/objects/chest_idle.png";
    openAnim = "images/objects/chest_open.png";
    openSfx = "";
  }
}
