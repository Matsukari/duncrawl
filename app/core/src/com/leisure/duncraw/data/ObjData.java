package com.leisure.duncraw.data;

import java.util.HashMap;

import lib.math.Pointi;

public class ObjData extends Dat { 
  public HashMap<String, String> anims;
  public HashMap<String, String> sounds;
  public String name;
  public String desc;
  public Pointi size;
  public float offsetX;
  public float offsetY;

  @Override
  public void reset() {
    super.reset();
    anims = new HashMap<>();
    anims.put("idle", DEFAULT_PNG);
    sounds = new HashMap<>();
    name = "No-name-obj";
    desc = "No-desc-obj";
    size = new Pointi();
    size.x = 1;
    size.y = 1;
    offsetX = 0;
    offsetY = 0;
  }
}
