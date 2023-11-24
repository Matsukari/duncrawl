package com.leisure.duncraw.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
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
  public void save() {
    if (ini == null) return;
    Json json = new Json(OutputType.json);
    ini.writeString(json.prettyPrint(json.toJson(this)), false);
  }
  public AssetSource load() throws Exception {
    Json json = new Json(OutputType.json);
    AssetSource data = json.fromJson(AssetSource.class, instance.ini);
    if (data != null) {
      Logger.log("AssetSource", "Loaded data");
      return data;
    }
    else {
      Logger.log("AssetSource", "Loading failed");
      throw new Exception();
    }
  }
  public static SaveData getSaveData() { 
    FileHandle saveFile = Gdx.files.local(instance.save);
    SaveData file = new SaveData(saveFile, new Status(), new Inventory()); 
    try { file = SaveData.load(saveFile); }
    catch (Exception e) { e.printStackTrace(); }
    // This new SaveData is constructed defaultly, so set it right
    file.overwrite(saveFile);
    file.save();
    return file; 
  }
  public static FloorData getFloorsData() { return new FloorData(); }
  public static void init(FileHandle ini) {
    instance = new AssetSource(ini);
    try { instance = instance.load(); }
    catch (Exception e) {
      e.printStackTrace();
      Logger.log("AssetSource", "Cannot parse ini file; reverting to default");
      instance.save = "save/dungeon_crawler.dat";
      instance.floors = "dat/floors.dat";
      instance.monsters = "dat/monsters.dat";
      instance.objects = "dat/objects.dat";
      instance.npcs = "dat/npcs.dat";
      instance.save();
    }
  }
}
