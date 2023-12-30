package com.leisure.duncraw.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.art.map.Terrain;

public class WallType {
  public static int LEFT_EDGE = 0;
  public static int BODY = 1;
  public static int RIGHT_EDGE = 2;
  public static int LEFT_CORNER = 3;
  public static int DOWN_EDGE = 4;
  public static int RIGHT_CORNER = 5;
  public static int SINGLE_BODY = 6;
  public static int SINGLE_DOWN = 7;
  public static int LEFT_HEAD = 8;
  public static int RIGHT_HEAD = 9;
  public static int TOP_HEAD = 10;
  public static int getTotal() { return 11; }
  public static TerrainVariants[] getAllWallTypes(Tileset tileset, SpriteBatch batch) {
    TerrainVariants types[] = new TerrainVariants[getTotal()];
    types[WallType.BODY] = tileset.getTerrainVariants("wall", batch);
    types[WallType.BODY].get(0).canTravel = false;
    types[WallType.LEFT_EDGE] = tileset.getTerrainVariants("wall_left_edge", batch);
    types[WallType.LEFT_CORNER] = tileset.getTerrainVariants("wall_left_corner", batch);
    types[WallType.LEFT_HEAD] = tileset.getTerrainVariants("left_wall", batch);
    types[WallType.RIGHT_EDGE] = tileset.getTerrainVariants("wall_right_edge", batch);
    types[WallType.RIGHT_CORNER] = tileset.getTerrainVariants("wall_right_corner", batch);
    types[WallType.RIGHT_HEAD] = tileset.getTerrainVariants("right_wall", batch);
    types[WallType.SINGLE_BODY] = tileset.getTerrainVariants("wall_single_body", batch);
    types[WallType.SINGLE_DOWN] = tileset.getTerrainVariants("wall_single_down", batch);
    types[WallType.DOWN_EDGE] = tileset.getTerrainVariants("wall_down_edge", batch);
    types[WallType.TOP_HEAD] = tileset.getTerrainVariants("top_wall", batch);
    for (TerrainVariants wallVar : types) {
      for (Terrain wall : wallVar) wall.canTravel = false;
    }
    return types; 
     
  } 
  public void setTraversable(boolean v, TerrainVariants i) {
    for (Terrain wall : i) wall.canTravel = v;
  }
}
