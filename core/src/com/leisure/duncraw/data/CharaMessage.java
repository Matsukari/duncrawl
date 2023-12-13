package com.leisure.duncraw.data;

public class CharaMessage extends Dat {
  public String message;
  public String sender;
  public String specialState;
  @Override
  public void reset() {
    message = "Default";
    sender = "Unknown";
    specialState = "normal";
  }
}
