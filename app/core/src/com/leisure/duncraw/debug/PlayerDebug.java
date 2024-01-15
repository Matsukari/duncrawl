package com.leisure.duncraw.debug;

import com.badlogic.gdx.Gdx;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.observers.IlluminateBehaviour;
import com.leisure.duncraw.art.item.Item;

import imgui.ImGui;

public class PlayerDebug extends CharaDebug {
  private Player player;
  private float modulate[] = { 0.967f, 0.667f, 0.8f, 0.091f };
  private float lightSize[] = { 227 };
  public PlayerDebug(Player inv) { 
    super(inv);
    player = inv; 
    id = "Player";
    size.y = Gdx.graphics.getHeight()*0.7f;
  } 
  @Override
  public void tool() {
    super.tool();
    ImGui.labelText("state", player.state.getClass().getSimpleName());
    if (player.prevState != null) ImGui.labelText("prev state", player.prevState.getClass().getSimpleName());
    ImGui.sliderFloat4("Light color", modulate, 0, 1);
    ImGui.sliderFloat("Light Size", lightSize, 100f, 800f);
    player.observers.get(IlluminateBehaviour.class).light.tint.set(modulate[0], modulate[1], modulate[2], modulate[3]);
    player.observers.get(IlluminateBehaviour.class).light.bounds.setSize(lightSize[0], lightSize[0]);
    ImGui.beginChild("Scroll panel", size.x, player.inventory.items.size() * 10 + 10);
    for (Item item : player.inventory.items) ImGui.text(String.format("%s", item.dat.name));
    ImGui.endChild();
    if (player.weapon != null) ImGui.labelText("Weapon", player.weapon.dat.name);
  
  }
}
