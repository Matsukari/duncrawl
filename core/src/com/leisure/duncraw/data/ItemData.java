package com.leisure.duncraw.data;

import java.util.HashMap;

public class ItemData extends Dat { 
  public HashMap<String, String> anims;
  public HashMap<String, String> sounds;
  public boolean oneTexture;

  @Override
  public void reset() {
    anims = new HashMap<>();
    anims.put("drop", "images/items/drop.png");
    anims.put("store", DEFAULT_PNG);
    oneTexture = true;
    sounds = new HashMap<>();
    sounds.put("store", DEFAULT_AUD);
  }
  public void passTo(ObjData obj) {
    obj.anims = new HashMap<>(anims);
    obj.sounds = new HashMap<>(sounds);
  }
}

