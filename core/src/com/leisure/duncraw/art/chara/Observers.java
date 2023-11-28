package com.leisure.duncraw.art.chara;

import java.util.ArrayList;

import com.leisure.duncraw.logging.Logger;

public class Observers {
  private final Chara chara;
  private final ArrayList<Observer> observers = new ArrayList<>();
  public Observers(Chara chara) { this.chara = chara; }
  public void add(Observer ob) { observers.add(ob); ob.init(chara); }
  public void notifyAll(State state) { 
    for (int i = 0; i < observers.size(); i++) notify(i, state); 
  }
  public void notify(int index, State state) {
    // switch (action) {
    //   case MOVE: observers.get(index).onMove();
    //   case HURT: observers.get(index).onHurt();
    //   case ATTACK: observers.get(index).onAttack();
    //   case INTERACT: observers.get(index).onInteract();
    //   default: 
    // }
    Logger.log("Observers", "Notified" + state.getClass().getName());
    observers.get(index).invoke(state);
  }
}