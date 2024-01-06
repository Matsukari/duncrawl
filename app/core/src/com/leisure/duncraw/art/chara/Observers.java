package com.leisure.duncraw.art.chara;

import java.util.ArrayList;

public class Observers {
  private final Chara chara;
  private final ArrayList<Observer> observers = new ArrayList<>();
  public Observers(Chara chara) { this.chara = chara; }
  public void add(Observer ob) { observers.add(ob); ob.init(chara); }
  public void notifyAll(State state) { 
    for (int i = 0; i < observers.size(); i++) notify(i, state); 
  }
  public void updateAll() {
    for (int i = 0; i < observers.size(); i++) observers.get(i).update(); 
  }
  public <T extends Observer> ArrayList<T> getAll(Class<T> clazz) {
    ArrayList<T> array = new ArrayList<>();
    for (Observer observer : observers) {
      if (observer.getClass() == clazz) array.add(clazz.cast(observer));
    }
    return array;
  }
  public <T extends Observer> T get(Class<T> clazz) { return getAll(clazz).get(0); }
  public void notify(int index, State state) {
    // Logger.log("Observers", "Notified" + state.getClass().getName());
    observers.get(index).invoke(state);
  }
}
