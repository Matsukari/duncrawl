package com.leisure.duncraw.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.leisure.duncraw.logging.Logger;

public class SaveData {
  private transient FileHandle file;
  public Progression progression;
  public Status status;
  public Settings settings;
  public SaveData(FileHandle file, com.leisure.duncraw.art.chara.Status stats, Inventory inventory) {
    progression = new Progression();
    status = new Status(stats, inventory);
    status.init();
    settings = new Settings(); 
    settings.reset();
    this.file = file;
    assert (!file.exists());
  }
  public SaveData() {

  }
  public void overwrite(FileHandle newFile) { file = newFile; }
  public void save() {
    Json json = new Json(OutputType.json);
    file.writeString(json.prettyPrint(json.toJson(this)), false);
  }
  public static SaveData load(FileHandle f) throws Exception {
    Json json = new Json(OutputType.json);
    SaveData data = json.fromJson(SaveData.class, f);
    if (data != null) {
      Logger.log("SaveData", "Loaded data");
      return data;
    }
    Logger.log("SaveData", "Loading failed");
    throw new Exception();
  }
}
