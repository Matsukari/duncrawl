package com.leisure.duncraw.data;

import java.util.HashMap;

public class ItemData extends Dat { 
  public HashMap<String, String> anims;
  public HashMap<String, String> sounds;
  public boolean oneTexture;

  @Override
  public void reset() {
    anims = new HashMap<>();
    anims.put("drop", DEFAULT_AUD);
    anims.put("store", DEFAULT_AUD);
    oneTexture = true;
    sounds = new HashMap<>();
    sounds.put("store", DEFAULT_AUD);
  }
}

