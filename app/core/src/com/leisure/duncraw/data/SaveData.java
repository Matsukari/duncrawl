package com.leisure.duncraw.data;

public class SaveData {
  public Progression progression;
  public Status status;
  public Settings settings;
  public SaveData(com.leisure.duncraw.art.chara.Status stats, Inventory inventory) {
    progression = new Progression();
    status = new Status(stats, inventory);
    status.init();
    settings = new Settings(); 
    settings.reset();
  }
  public SaveData() {}
}
