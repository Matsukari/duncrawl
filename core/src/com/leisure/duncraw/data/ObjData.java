package com.leisure.duncraw.data;

import java.util.HashMap;

public class ObjData extends Dat { 
  public HashMap<String, String> anims;
  public HashMap<String, String> sounds;
  public boolean oneTexture;

  @Override
  public void reset() {
    anims = new HashMap<>();
    anims.put("idle", DEFAULT_PNG);
    oneTexture = true;
    sounds = new HashMap<>();
  }
}
