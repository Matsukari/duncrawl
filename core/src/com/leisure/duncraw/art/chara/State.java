package com.leisure.duncraw.art.chara;

public abstract class State {
  private Chara chara;
  public State next;
  public State(State n) { next = n; }
  public State() { next = null; }
  public void init(Chara s) { chara = s; }
  public abstract void update(float dt);
}
