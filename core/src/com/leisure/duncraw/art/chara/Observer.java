package com.leisure.duncraw.art.chara;

public abstract class Observer {
  public Chara chara;
  public void init(Chara c) { chara = c; }
  public abstract void invoke(State state);
  public abstract Observer copy();
  public void update() {}
}
