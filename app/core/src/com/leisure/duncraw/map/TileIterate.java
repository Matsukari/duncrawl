package com.leisure.duncraw.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

@FunctionalInterface
public interface TileIterate {
  public void onTile(TiledMapTile tile); 
}
