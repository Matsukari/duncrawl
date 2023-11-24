package com.leisure.duncraw.story;

public class Quest {
  public String desc;
  public final Scene finishScene;
  public final Quest chain;
  public Quest(Scene finishScene, Quest chain) {
    this.finishScene = finishScene;
    this.chain = chain;
  }
}
