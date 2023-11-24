package com.leisure.duncraw.map.loader;

import com.badlogic.gdx.files.FileHandle;
import com.leisure.duncraw.map.TerrainSet;

public class TmxLoader extends TerrainSetLoader {
  private final FileHandle file;
  public TmxLoader(FileHandle file) {
    this.file = file;
  }
  @Override
  public TerrainSet load() {
    return null;
  }
}
