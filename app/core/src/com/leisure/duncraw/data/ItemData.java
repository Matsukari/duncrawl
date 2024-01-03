package com.leisure.duncraw.data;

import java.util.HashMap;

import lib.math.Pointi;

public class ItemData extends ObjData { 
  @Override
  public void reset() {
    anims = new HashMap<>();
    anims.put("drop", "images/items/drop.png");
    anims.put("store", DEFAULT_PNG);
    sounds = new HashMap<>();
    sounds.put("store", DEFAULT_AUD);
    name = "No-name-obj";
    desc = "No-desc-obj";
    size = new Pointi();
    size.x = 1;
    size.y = 1;
    offsetX = 0;
    offsetY = 0;
  }
}

