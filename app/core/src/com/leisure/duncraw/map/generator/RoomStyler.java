package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.leisure.duncraw.art.map.Obj;

public class RoomStyler {
  public ArrayList<ObjStyle> requires;
  public static class ObjStyle {
    public Obj obj;
    public int min = 1;
    public int max = 1;
    public Type type = Type.CORNER_OBJ;
    public static enum Type {
      WALL_OBJ, SECRET_OBJ, HIDDEN_OBJ, CORNER_OBJ, CENTER_OBJ
    };
  }
  public void style() {
    
  }
}
