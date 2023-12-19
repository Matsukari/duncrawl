package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.data.CharasData;
import com.leisure.duncraw.manager.CharaManager;

public abstract class Spawner {
  final public CharasData sources;
  final protected CharaManager charaManager;
  public Spawner(CharaManager charaManager, CharasData sources) {
    this.charaManager = charaManager;
    this.sources = sources;
  }
  public abstract Chara spawn(String source);
}
