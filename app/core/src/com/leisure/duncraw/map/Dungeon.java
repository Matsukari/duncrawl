package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.leisure.duncraw.manager.CharaManager;

public class Dungeon {
  ArrayList<Tilemap> floors = new ArrayList<>();
  public Dungeon() {}
  public void render() {
  }
  public static Tilemap makeFloorFromTmx(FileHandle file) {
    return null;
  }
}
