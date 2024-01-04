package com.leisure.duncraw.art;

import java.util.ArrayList;

import com.leisure.duncraw.manager.RenderSortManager;

public class EntityGroup <T extends Art> {
  public final RenderSortManager manager; 
  public EntityGroup(RenderSortManager manager) {
    assert manager != null;
    this.manager = manager;
  }
  public void add(T e) {
    manager.entities.add(e);
  }
  public void remove(Object o) {
    manager.entities.remove(o);
  }
  public void clear() {
  }
}
