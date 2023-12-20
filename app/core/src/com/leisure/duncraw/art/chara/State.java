package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.art.chara.states.NoState;

public abstract class State {
  public Chara chara;
  public State next;
  public State() { }
  public State(State n) { next = n; }
  public void init(Chara s) { chara = s; }
  public abstract void update(float dt);
  public State copy() { return new NoState(); }
}
