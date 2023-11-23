package com.leisure.duncraw.tooling;

public class ToolAgent {
  public String id = "Tool";
  public ToolAgent(String id) { this.id = id; }
  public void tool() {
    
  }
  @Override
  public boolean equals(Object obj) {
    return ((ToolAgent)obj).id == id;
  }
}
