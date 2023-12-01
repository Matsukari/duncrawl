package com.leisure.duncraw.debug;

import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.item.Item;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import lib.tooling.ToolAgent;

public class PlayerDebug extends ToolAgent {
  Player player;
  public PlayerDebug(Player inv) { 
    player = inv; 
    id = "Player";
    size.y = Gdx.graphics.getHeight()*0.7f;
  } 
  @Override
  public void tool() {
    if (ImGui.collapsingHeader("Inventory", ImGuiTreeNodeFlags.DefaultOpen)) {
      ImGui.beginChild("Scroll panel", size.x, 100);
      for (Item item : player.inventory.data) { 
        ImGui.text(String.format("%s", item.dat.name));
      }
      ImGui.endChild();
    }
    if (player.itemSel != null) {
      ImGui.labelText("Equip", player.itemSel.dat.name);
    }
  }
}
