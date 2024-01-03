package com.leisure.duncraw.art;

import java.util.HashMap;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.manager.RenderSortManager;

public class EntityHashMap <K, T extends Art> extends EntityGroup <T> {
  public final HashMap<K, T> data = new HashMap<>();
  public EntityHashMap(RenderSortManager manager) { super(manager); }
  @Override
  public void remove(Object o) {
    data.remove(o);
    super.remove(o);
  }
  public T get(K key) { return data.get(key); }
  public void put(K key, T value) {
    data.put(key, value);
    add(value);
  }
  
}
