package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.manager.CharaManager;

public class Spawner {
  final public CharasData sources;
  final protected CharaManager charaManager;
  public Spawner(CharaManager charaManager) {
    this.charaManager = charaManager;
    this.sources = charaManager.sources;
  }
  public <T extends Chara> T spawn(T chara) {
    return charaManager.add(chara);
  }
}
