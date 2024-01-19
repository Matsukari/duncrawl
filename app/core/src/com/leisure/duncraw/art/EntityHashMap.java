package com.leisure.duncraw.art;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.RenderSortManager;

public class EntityHashMap <K, T extends Art> extends EntityGroup <T> {
  public final HashMap<K, T> data = new HashMap<>();
  public EntityHashMap(RenderSortManager manager) { super(manager); }
  public void removeKey(Object key) {
    Object item = data.get(key);
    data.remove(key);
    remove(item);
  }
  public T get(K key) { return data.get(key); }
  public void put(K key, T value) {
    data.put(key, value);
    add(value);
  }
  @Override
  public void clear() {
    Logger.log("EntityHashMap", String.format("(Shared) Before clear: %d", data.size()));
    Logger.log("EntityHashMap", String.format("(All) Before clear: %d", manager.entities.size()));
    ArrayList<T> values = new ArrayList<>();
    for (Map.Entry<K, T> entry : data.entrySet()) {
      // values.add(entry.getValue());
      remove(entry.getValue());
      
      // Logger.log("EntityHashMap", "Removing...");
    } 
    // manager.entities.removeAll(values);
    data.clear();
    Logger.log("EntityHashMap", String.format("(Shared) After clear: %d", data.size()));
    Logger.log("EntityHashMap", String.format("(All) After clear: %d", manager.entities.size()));
  }
  
}
