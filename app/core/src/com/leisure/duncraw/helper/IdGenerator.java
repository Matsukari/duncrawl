package com.leisure.duncraw.helper;


public class IdGenerator {
  private static int last = 0;
  public static int gen() { 
    // Logger.log("IdGenerator", "Generated id for unique resource");
    last += 1; 
    return last;
  }
}
