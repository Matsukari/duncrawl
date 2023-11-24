package com.leisure.duncraw.data;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.leisure.duncraw.logging.Logger;

public class Serializer {
  public static <T> void save(T value, FileHandle file) {
    Logger.log("Serializer", "Writing to file: " + file.toString());
    Json json = new Json(OutputType.json);
    file.writeString(json.prettyPrint(json.toJson(value)), false); 
  }
}
