package com.leisure.duncraw.map.loader;

import java.util.ArrayList;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.art.item.ItemParser;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.ObjParser;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.TerrainSet;

import lib.animation.LinearAnimation;

public class TmxLoader {
  public static TerrainSet load(String file, SpriteBatch batch, int width, int height) {
    TiledMap tiled = new TmxMapLoader().load(file);
    TiledMapTileLayer defLayer = (TiledMapTileLayer)tiled.getLayers().get(0);
    TerrainSet terrainSet = new TerrainSet(defLayer.getWidth(), defLayer.getHeight(), width, height);
    String ar = "";
    for (MapLayer l : tiled.getLayers()) {
      if (l instanceof TiledMapTileLayer) {
        TiledMapTileLayer layer = (TiledMapTileLayer)l;
        for (int y = 0; y < layer.getHeight(); y++) {
          for (int x = 0; x < layer.getWidth(); x++) {
            Cell cell = layer.getCell(x, y);
            if (cell == null) continue;
            String objType = cell.getTile().getProperties().get("object", String.class);
            String itemType = cell.getTile().getProperties().get("item", String.class);
            String objDat = cell.getTile().getProperties().get("dat", String.class);
            Obj obj = null;
            if (objType != null && objDat != null) obj = ObjParser.from(objType, objDat, batch);
            else if (itemType != null && objDat != null) obj = ItemParser.from(itemType, objDat, batch);
            else {
              Terrain terrain = new Terrain(batch, new LinearAnimation<TextureRegion>(cell.getTile().getTextureRegion()));
              terrain.bounds.setSize(width, height);
              terrainSet.putTerrain(terrain, x, y); 
            }
            if (obj != null) {
              obj.bounds.setSize(width, height);
              terrainSet.putObject(obj, x, y);

            }
            ar = ar + "#";
          }
          ar = ar + "\n";
        }
      }
    }

    // Logger.log("TmxLoader", ar);
    return terrainSet;
  }
}
