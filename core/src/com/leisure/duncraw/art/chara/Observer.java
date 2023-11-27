package com.leisure.duncraw.art.chara;

import com.leisure.duncraw.art.chara.states.InteractState;

public abstract class Observer {
  public Chara chara;
  public void init(Chara c) { chara = c; }
  public abstract void invoke(State state);
  // public abstract void onInteract(State state);
  // public abstract void onMove();  
  // public abstract void onAttack();
  // public abstract void onHurt();
}
