package com.leisure.duncraw.map.loader;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.CharaParser;
import com.leisure.duncraw.art.item.ItemParser;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.ObjParser;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.helper.Pair;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.RenderSortManager;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.TerrainSet;
import com.leisure.duncraw.screen.GameScreen.Context;

import lib.animation.LinearAnimation;
import lib.math.Pointi;

public class TmxLoader {
  public final ArrayList<Pair<Pointi, Chara>> charas = new ArrayList<>();
  private Context context;
  public TmxLoader() {}
  public TerrainSet load(Floor floor, Context context, String file) {
    this.context = context;
    TiledMap tiled = new TmxMapLoader().load(file);
    TiledMapTileLayer defLayer = (TiledMapTileLayer)tiled.getLayers().get(0);
    TerrainSet terrainSet = new TerrainSet(
        defLayer.getWidth(), defLayer.getHeight(), 
        floor.generator.data.tileSize, floor.generator.data.tileSize, 
        floor.generator.renderSortManager);
    ObjParser objParser = new ObjParser(floor, context);
    CharaParser charaParser = new CharaParser(floor, context);
    // TerrainSet foreground = new TerrainSet(defLayer.getWidth(), defLayer.getHeight(), width, height, renderSortManager);
    int terrains = 0;
    int objs = 0;
    int rooms = 0;
    for (MapLayer l : tiled.getLayers()) {
      Array<RectangleMapObject> rects = l.getObjects().getByType(RectangleMapObject.class);
      if (rects.size > 0) {
        for (RectangleMapObject r : rects) {
          Rectangle room = r.getRectangle();
          room.x *= 2;
          room.y *= 2;
          room.width *= 2;
          room.height *= 2;
          floor.generator.roomsBuilder.rooms.add(room);
          rooms ++;
        }
      }
      if (!(l instanceof TiledMapTileLayer)) continue; 
      TiledMapTileLayer layer = (TiledMapTileLayer)l;
      for (int y = 0; y < layer.getHeight(); y++) {
        for (int x = 0; x < layer.getWidth(); x++) {
          Cell cell = layer.getCell(x, y);
          if (cell == null) continue;
          TerrainSet terrainLayer = terrainSet;
          Pair<String, String> tileChara = getEntity(cell.getTile(), "chara");
          Pair<String, String> tileObj = getEntity(cell.getTile(), "object");

          // if (l.getName().contains("bg")) terrainLayer = terrainSet;
          // else terrainLayer = foreground;

          if (tileChara != null) {
            charas.add(new Pair<>(new Pointi(x, y), charaParser.from(tileChara.a, tileChara.b)));
          }
          else if (tileObj != null) {
            Obj obj = objParser.from(tileObj.a, tileObj.b); 
            terrainLayer.putObject(obj, x, y);
            objs++;
          }
          else {
            String terrainType = cell.getTile().getProperties().get("terrain", String.class);
            Terrain terrain = new Terrain(new LinearAnimation<TextureRegion>(cell.getTile().getTextureRegion()));
            if (terrainType != null && terrainType.contains("wall")) terrain.canTravel = false;
            terrainLayer.putTerrain(terrain, x, y); 
            terrains ++;
          }

        }
      }
    }
    Logger.log("TmxLoader", "Loaded " + Integer.toString(terrains) + " terrains");
    Logger.log("TmxLoader", "Loaded " + Integer.toString(charas.size()) + " charas");
    Logger.log("TmxLoader", "Loaded " + Integer.toString(objs) + " objs");
    Logger.log("TmxLoader", "Loaded " + Integer.toString(rooms) + " rooms");
    // Logger.log("TmxLoader", ar);
    return terrainSet;
  }
  private Pair<String, String> getEntity(TiledMapTile tile, String tag) {
    String type = tile.getProperties().get(tag, String.class);
    String dat = tile.getProperties().get("dat", String.class);
    // Logger.log("TmxLoader", String.format("%s %s", type, dat));
    if (type != null && dat != null) return new Pair<String, String>(type, dat);
    return null;
  }
}
