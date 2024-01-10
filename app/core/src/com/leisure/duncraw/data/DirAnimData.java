package com.leisure.duncraw.data;

public class DirAnimData extends Dat {
  public String side;
  public String back;
  public String front;
  public boolean rightHanded;
  @Override
  public void reset() {
    rightHanded = false;
    side = "";
    back = "";
    front = "";
  }
  @Override
  public boolean equals(Object obj) {
    DirAnimData other = (DirAnimData)obj;
    return side.equals(other.side) && back.equals(other.back) && front.equals(other.front);
  }
}
