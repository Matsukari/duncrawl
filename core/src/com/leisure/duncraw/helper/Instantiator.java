package com.leisure.duncraw.helper;

@FunctionalInterface
public interface Instantiator<T> {
  public T instance(); 
}
