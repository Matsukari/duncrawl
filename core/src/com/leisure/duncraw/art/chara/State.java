package com.leisure.duncraw.art.chara;

public abstract class State {
  private Status status;
  public void init(Status s) { status = s; }
  public abstract void update(float dt);
}
