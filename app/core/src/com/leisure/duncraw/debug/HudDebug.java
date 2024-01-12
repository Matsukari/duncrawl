package com.leisure.duncraw.debug;

import com.leisure.duncraw.manager.HudManager;

import imgui.ImGui;
import lib.tooling.ToolAgent;

public class HudDebug extends ToolAgent {
  private HudManager manager;
  public HudDebug(HudManager hudManager) {
    this.manager = hudManager;
    id = "HudManager";
  }
  @Override
  public void tool() {
    if (ImGui.button("Debug all")) {
      manager.root.setDebug(!manager.root.getDebug(), true);
    }
  }
  
}
