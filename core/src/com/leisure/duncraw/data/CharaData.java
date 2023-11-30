package com.leisure.duncraw.data;

import java.util.HashMap;

import com.leisure.duncraw.art.chara.Status;

public class CharaData extends Dat {
  public HashMap<String, DirAnimData> anims;
  public HashMap<String, String> sounds;
  public Status status;

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
    status = new Status();
    status.reset();
  }
}
