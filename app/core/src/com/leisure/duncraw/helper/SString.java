package com.leisure.duncraw.helper;

import com.badlogic.gdx.math.Rectangle;

public class SString {
  public static String toString(Rectangle rect) {
    return String.format("( %d %d %d %d )", 
        (int)rect.x, 
        (int)rect.y, 
        (int)rect.width, 
        (int)rect.height);
  }
}
