package com.leisure.duncraw.art.chara;

public abstract class State {
  public Chara chara;
  public State next;
  public State() { }
  public State(State n) { next = n; }
  public void init(Chara s) { chara = s; }
  public abstract void update(float dt);
}
