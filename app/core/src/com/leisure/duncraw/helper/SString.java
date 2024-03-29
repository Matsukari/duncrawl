package com.leisure.duncraw.helper;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Rectangle;

public class SString {
  public static String toString(Rectangle rect) {
    return String.format("( %d %d %d %d )", 
        (int)rect.x, 
        (int)rect.y, 
        (int)rect.width, 
        (int)rect.height);
  }
  public static String toString(HashMap<String, Integer> map) {
    String str = "";
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      str += String.format("\n[%s] %s", entry.getKey(), Integer.toString(entry.getValue().intValue()));
    }
    return str;
  }
  public static String sepLines(String str, int wordLimit) {
    String lines = "";
    for (int line = 0; line < str.length(); line+=wordLimit) {
      lines = lines + '\n' + str.substring(line, Math.min(line + wordLimit, str.length()));
    }
    return lines.substring(1); 
  }
}
