package com.leisure.duncraw.debug;

import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.data.Inventory;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import lib.tooling.ToolAgent;
import lib.tooling.Tooling;

public class InventoryDebug extends ToolAgent {
  Inventory inventory;
  public InventoryDebug(Inventory inv) { inventory = inv; }
  @Override
  public void tool() {
    if (ImGui.collapsingHeader("Logs", ImGuiTreeNodeFlags.DefaultOpen)) {
      ImGui.beginChild("Scroll panel");
      for (Item item : inventory.data) 
        ImGui.text(String.format("%s", item.dat.name));
      ImGui.endChild();
    }
  }
}
