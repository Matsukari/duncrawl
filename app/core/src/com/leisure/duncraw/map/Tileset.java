package com.leisure.duncraw.map;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

public class Tileset {
  public TiledMap tilesetsOwner;
  public TiledMapTileSets tilesets;
  public Tileset(String file) {
    // tilesetsOwner = new TmxMapLoader().loadSync(Graphics.assets, file, Gdx.files.internal(file), new TmxMapLoader.Parameters());
    tilesetsOwner = new TmxMapLoader().load(file);
    tilesets = tilesetsOwner.getTileSets();
  }
  public void onTileIteration(TileIterate iterate) {
    TiledMapTileSet tiles = null;
    if (tilesets.iterator().hasNext()) tiles = tilesets.iterator().next();
    else return;
    TiledMapTile tile = null;
    if (tiles.iterator().hasNext()) tile = tiles.iterator().next();

    for (Iterator<TiledMapTileSet> it = tilesets.iterator(); it.hasNext(); tiles = it.next()) {
      for (Iterator<TiledMapTile> it2 = tiles.iterator(); it2.hasNext(); tile = it2.next()) {
        iterate.onTile(tile);
      }
    }
  }
  public ArrayList<TiledMapTile> filter(String prop, String val) {
    ArrayList<TiledMapTile> tiles = new ArrayList<>();
    onTileIteration((tile)-> {
      String thisVal = tile.getProperties().get(prop, String.class);
      if (thisVal == null) return;
      else if (thisVal.equalsIgnoreCase(val)) tiles.add(tile);
      // else Logger.log("Tileset", String.format("%s is not a valid terrain", terrain));
    });
    Logger.log("Tileset", "Filtered terrains: " + Integer.toString(tiles.size()));
    return tiles;
  }
  public ArrayList<Terrain> terrainTransform(ArrayList<TiledMapTile> tiles, SpriteBatch batch) {
    ArrayList<Terrain> terrains = new ArrayList<>();
    for (TiledMapTile tile : tiles) {
      Terrain terrain = new Terrain(batch, new LinearAnimation<TextureRegion>(tile.getTextureRegion()));
      terrain.bounds.setSize(tile.getTextureRegion().getRegionWidth(), tile.getTextureRegion().getRegionHeight());
      terrains.add(terrain);
    }
    return terrains;
  }
  public TerrainVariants getTerrainVariants(String val, SpriteBatch batch) {
    return new TerrainVariants(terrainTransform(filter("terrain", val), batch));

  }
  public void dispose() {
    tilesetsOwner.dispose();
  }
}
