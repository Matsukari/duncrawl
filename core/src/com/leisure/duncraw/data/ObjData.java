package com.leisure.duncraw.data;

import java.util.HashMap;

public class ObjData extends Dat { 
  public HashMap<String, String> anims;
  public HashMap<String, String> sounds;
  public boolean oneTexture;
  public String name;
  public String desc;

  @Override
  public void reset() {
    anims = new HashMap<>();
    anims.put("idle", DEFAULT_PNG);
    oneTexture = true;
    sounds = new HashMap<>();
    name = "No-name-obj";
    desc = "No-desc-obj";
  }
}
