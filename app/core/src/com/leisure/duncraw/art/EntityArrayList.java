package com.leisure.duncraw.art;

import java.util.ArrayList;

import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.RenderSortManager;

public class EntityArrayList <T extends Art> extends EntityGroup<T> {
  public final ArrayList<T> data = new ArrayList<>();
  public EntityArrayList(RenderSortManager manager) { super(manager); }
  @Override
  public void add(T e) {
    synchronized (data) {
      data.add(e);
    }
    super.add(e);
  }
  @Override
  public void remove(Object o) {
    synchronized (data) {
      data.remove(o);
    }
    super.remove(o);
  }  
  @Override
  public void clear() {
    Logger.log("EntityHashMap", String.format("(Shared) Before clear: %d", data.size()));
    Logger.log("EntityHashMap", String.format("(All) Before clear: %d", manager.entities.size()));
    manager.entities.removeAll(data);
    Logger.log("EntityHashMap", String.format("(Shared) After clear: %d", data.size()));
    Logger.log("EntityHashMap", String.format("(All) After clear: %d", manager.entities.size()));
  }
}
