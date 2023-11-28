package com.leisure.duncraw.art.chara;

import java.util.HashMap;


public class DirAnimationMap {
  public final HashMap<String, DirAnimation> data = new HashMap<>();
  public DirAnimation current; 
  public void set(String anim) { set(anim, 0, 0); }
  public void set(String anim, int dirX, int dirY) {
    DirAnimation a = data.get(anim);
    if (a != null) current = a;
    current.face(dirX, dirY);
  }
}
