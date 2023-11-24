package com.leisure.duncraw.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.leisure.duncraw.art.chara.Status;
import com.leisure.duncraw.logging.Logger;

public class AssetSource {
  public transient final FileHandle ini;
  public String save;
  public String floors;
  public String monsters;
  public String objects;
  public String npcs;
  public static AssetSource instance;
  public AssetSource(FileHandle ini) {
    this.ini = ini; 
  }
  public AssetSource() { ini = null; }
  public static SaveData getSaveData() { 
    FileHandle saveFile = Gdx.files.local(instance.save);
    SaveData save = new SaveData(new Status(), new Inventory()); 
    try { save = Deserializer.load(SaveData.class, saveFile); }
    catch (Exception e) { e.printStackTrace(); }
    // This new SaveData is constructed defaultly, so set it right
    Serializer.save(save, saveFile);
    return save; 
  }
  public static void save() { Serializer.save(instance, instance.ini); }
  public static AssetSource load() throws Exception { return Deserializer.load(AssetSource.class, instance.ini); }
  public static FloorData getFloorsData() { 
    FloorData dat;
    try { dat = Deserializer.load(FloorData.class, Gdx.files.local(instance.floors)); }
    catch (Exception e) { 
      e.printStackTrace(); 
      dat = new FloorData(); 
      dat.reset(); 
      Serializer.save(dat, Gdx.files.local(instance.floors));
    }
    return dat;
  }
  public static void init(FileHandle ini) {
    instance = new AssetSource(ini);
    try { instance = AssetSource.load(); }
    catch (Exception e) {
      e.printStackTrace();
      Logger.log("AssetSource", "Cannot parse ini file, so it was defaulted");
      instance.save = "save/dungeon_crawler.dat";
      instance.floors = "dat/floors.dat";
      instance.monsters = "dat/monsters.dat";
      instance.objects = "dat/objects.dat";
      instance.npcs = "dat/npcs.dat";
      AssetSource.save();
    }
  }
}
