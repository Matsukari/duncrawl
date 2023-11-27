package com.leisure.duncraw.map.loader;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.TerrainSet;

public class TmxLoader {
  public static TerrainSet load(String file, SpriteBatch batch, int width, int height) {
    TiledMap tiled = new TmxMapLoader().load(file);
    TiledMapTileLayer defLayer = (TiledMapTileLayer)tiled.getLayers().get(0);
    TerrainSet terrainSet = new TerrainSet(defLayer.getWidth(), defLayer.getHeight(), width, height);
    String ar = "";
    for (MapLayer l : tiled.getLayers()) {
      TiledMapTileLayer layer = (TiledMapTileLayer)l;
      for (int y = 0; y < layer.getHeight(); y++) {
        for (int x = 0; x < layer.getWidth(); x++) {
          int yy = layer.getHeight() - 1 - y;
          Cell cell = layer.getCell(x, y);
          if (cell == null) continue;
          Terrain terrain = new Terrain(batch, cell.getTile().getTextureRegion());
          terrain.bounds.setSize(width, height);
          terrainSet.putTerrain(terrain, x, y); 
          ar = ar + "#";
        }
        ar = ar + "\n";
      }
    }

    Logger.log("TmxLoader", ar);
    return terrainSet;
  }
}
