package com.leisure.duncraw.data;

import java.util.HashMap;

public class ItemData extends ObjData { 
  // public HashMap<String, String> anims;
  // public HashMap<String, String> sounds;
  // public String name;
  // public String desc;

  @Override
  public void reset() {
    anims = new HashMap<>();
    anims.put("drop", "images/items/drop.png");
    anims.put("store", DEFAULT_PNG);
    sounds = new HashMap<>();
    sounds.put("store", DEFAULT_AUD);
    name = "No-name-obj";
    desc = "No-desc-obj";
  }
}

