package com.leisure.duncraw.data;

import java.util.HashMap;

public class ObjData extends Dat { 
  public HashMap<String, String> anims;
  public HashMap<String, String> sounds;
  public String name;
  public String desc;
  @Override
  public void reset() {
    super.reset();
    anims = new HashMap<>();
    anims.put("idle", DEFAULT_PNG);
    sounds = new HashMap<>();
    name = "No-name-obj";
    desc = "No-desc-obj";
  }
}
