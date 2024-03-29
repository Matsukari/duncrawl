package com.leisure.duncraw.data;

import java.util.HashMap;

import com.leisure.duncraw.art.chara.Status;

import lib.math.Pointi;


public class CharaData extends Dat {
  public HashMap<String, DirAnimData> anims;
  public HashMap<String, String> sounds;
  public Status status;
  public Pointi size;
  public float offsetX;
  public float offsetY;
  public boolean knockable = true;

  @Override
  public void reset() {
    anims = new HashMap<>();
    DirAnimData template = new DirAnimData();
    template.reset();
    anims.put("idle", template);
    anims.put("move", template);
    anims.put("attack", template);
    anims.put("hurt", template);
    sounds = new HashMap<>();
    size = new Pointi();
    size.x = 1;
    size.y = 1;
    offsetX = 0;
    offsetY = 0;
    status = new Status();
    status.reset();
  }
  @Override
  public boolean equals(Object obj) {
    return anims.equals(obj);
  }
}
