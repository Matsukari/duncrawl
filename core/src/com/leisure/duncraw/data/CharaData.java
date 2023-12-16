package com.leisure.duncraw.data;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.art.chara.Status;

public class CharaData extends Dat {
  public HashMap<String, DirAnimData> anims;
  public HashMap<String, String> sounds;
  public Status status;

  public static CharaData fromDat(String source) {
    CharaData data = new CharaData();
    data.reset();
    try { data = Deserializer.load(CharaData.class, Gdx.files.local(source)); } 
    catch (Exception e) { Serializer.save(data, Gdx.files.local(source)); }
    return data;
  } 
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
    // sounds.put("move", "default");
    // sounds.put("attack", "default");
    // sounds.put("hurt", "default");
    status = new Status();
    status.reset();
  }
}
