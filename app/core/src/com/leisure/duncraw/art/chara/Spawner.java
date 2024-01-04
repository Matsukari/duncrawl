package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.manager.CharaManager;

public abstract class Spawner {
  final public CharasData sources;
  final protected CharaManager charaManager;
  public Spawner(CharaManager charaManager) {
    this.charaManager = charaManager;
    this.sources = charaManager.sources;
  }
  public abstract Chara spawn(String source);
}
