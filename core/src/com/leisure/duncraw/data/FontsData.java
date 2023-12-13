package com.leisure.duncraw.data;

public class FontsData extends Dat {;
  public String def;
  public String normal;
  public String important;
  public String reading;
  @Override
  public void reset() {
    def = "fonts/default.fnt";
    normal = "fonts/normal.fnt";
    important = "fonts/important.fnt";
    reading = "fonts/reading.fnt";
  }
}
