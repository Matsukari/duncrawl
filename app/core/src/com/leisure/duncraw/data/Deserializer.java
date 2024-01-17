package com.leisure.duncraw.data;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
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
  // :)
  // Guranties return non-null since the application will quit before ever reaching default return
  public static <T extends Dat> T safeLoad(Class<T> tClass, FileHandle file) {
    // Must have default constructor
    T instance;
    try {
      instance = load(tClass, file);
      return instance;
    } 
    catch (InvocationTargetException e) { e.printStackTrace(); System.exit(-1); }
    catch (Exception e) {
      e.printStackTrace();
      try {
        Logger.log("Deserializer", String.format("Loading failed (%s)", tClass.getSimpleName()));
        instance = tClass.getDeclaredConstructor().newInstance(); 
        instance.reset();
        Serializer.save(instance, file); 
        return instance;
      }
      catch (Exception e2) { e2.printStackTrace(); System.exit(-1); }
    }
    return null;
  }
  // Local directory
  public static <T extends Dat> T safeLoad(Class<T> clazz, String file) { return safeLoad(clazz, Gdx.files.local(file)); }
}
