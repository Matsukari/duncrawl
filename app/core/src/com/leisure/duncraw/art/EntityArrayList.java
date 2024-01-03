package com.leisure.duncraw.art;

import java.util.ArrayList;

import com.leisure.duncraw.manager.RenderSortManager;

public class EntityArrayList <T extends Art> extends EntityGroup<T> {
  public final ArrayList<T> data = new ArrayList<>();
  public EntityArrayList(RenderSortManager manager) { super(manager); }
  @Override
  public void add(T e) {
    data.add(e);
    super.add(e);
  }
  @Override
  public void remove(Object o) {
    data.remove(o);
    super.remove(o);
  }  
}
