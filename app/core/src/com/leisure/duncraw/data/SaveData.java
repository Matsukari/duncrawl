package com.leisure.duncraw.data;

import com.leisure.duncraw.art.chara.Status;

public class SaveData {
  public Progression progression;
  public PlayerData player;
  public Settings settings;
  public SaveData(Status stats, Inventory inventory) {
    stats.reset();
    inventory.reset();
    progression = new Progression();
    progression.reset();
    player = new PlayerData(stats, inventory);
    player.reset();
    settings = new Settings(); 
    settings.reset();
  }
  public SaveData() {}
}
