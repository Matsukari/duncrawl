package com.leisure.duncraw.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

public class AArray <T> extends ArrayList<T> {
  public T getIf(Predicate<T> predicate) {
    T result = null;
    for (T e : this) { 
      if (predicate.test(e)) result = e; 
    }
    return result;
  } 
  
}
