package com.leisure.duncraw.debug;

import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.item.Item;

import imgui.ImGui;
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
      ImGui.labelText("state", player.state.getClass().getSimpleName());
      ImGui.beginChild("Scroll panel", size.x, player.inventory.data.size() * 10 + 10);
      for (Item item : player.inventory.data) { 
        ImGui.text(String.format("%s", item.dat.name));
      }
      ImGui.endChild();
    if (player.itemSel != null) {
      ImGui.labelText("Equip", player.itemSel.dat.name);
    }
  }
}
