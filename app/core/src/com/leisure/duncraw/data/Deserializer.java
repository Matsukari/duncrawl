package com.leisure.duncraw.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.files.FileHandle;
import com.leisure.duncraw.logging.Logger;

public class Deserializer {
  public static <T> T load(Class<T> tClass, FileHandle file) throws Exception {
    Json json = new Json(OutputType.json);
    T data = json.fromJson(tClass, file);
    if (data != null) {
      Logger.log("Deserializer", "Loaded data -> " + tClass.getSimpleName());
      return data;
    }
    else {
      Logger.log("Deserializer", String.format("Loading failed (%s)", tClass.getSimpleName()));
      throw new Exception();
    }
  }
}
