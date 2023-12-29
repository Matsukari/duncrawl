package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.TerrainSet;
import com.leisure.duncraw.map.TerrainVariants;
import com.leisure.duncraw.map.WallType;

import lib.math.Pointi;

public class TerrainSetGenerator {
  public RoomsBuilder roomsBuilder;
  public FloorData data;
  public final TerrainFurnishers wallFurnishers = new TerrainFurnishers();
  public final TerrainFurnishers groundFurnishers = new TerrainFurnishers();
  // 0-5 for a grid representation of the body of a wall
  // 0 1 2 
  // 3 4 5
  // Other oddity collections
  // 6 7 
  public TerrainVariants walls[] = new TerrainVariants[8];
  public TerrainVariants grounds = new TerrainVariants();
  public TerrainSetGenerator(FloorData data) {
    this.data = data;
    roomsBuilder = new RoomsBuilder(data.tileSize);
  }
  public TerrainSet prepare() {
    roomsBuilder.build(data.roomsNum, new Vector2(data.getMaxWidth(), data.getMaxHeight()), data.widthRange, data.heightRange);
    TerrainSet terrainSet = new TerrainSet(
        (int)(roomsBuilder.rect.width/data.tileSize)+1, 
        (int)(roomsBuilder.rect.height/data.tileSize)+1, 
        data.tileSize, data.tileSize);
    Logger.log("TerrainSetGenerator", String.format("Size of terrain be generated: %d %d", terrainSet.cols, terrainSet.rows));
    // Logger.log("TerrainSetGenerator", roomsBuilder.rect.toString());
    ArrayList<Rectangle> expandedCorridors = roomsBuilder.expandCorridors(4, false);
    roomsBuilder.rooms.addAll(expandedCorridors);
    
    return terrainSet;
  }
  public void populate(TerrainSet terrainSet) {
    try { 
      placeGrounds(terrainSet, groundFurnishers);
      placeWalls(terrainSet, wallFurnishers);
    } catch (Exception e) { 
      e.printStackTrace();
      System.exit(-1);
    }
  }
  public void placeGrounds(TerrainSet terrainSet, TerrainFurnishers furnishers) throws Exception {
    roomsBuilder.forEachTileInRooms(roomsBuilder.rooms, (room, col, row)->{
      Pointi pos = roomsBuilder.getRoomRelTilePos(room);
      Terrain terrain = grounds.get(MathUtils.random(0, grounds.size()-1)).clone();
      if (terrainSet.getTerrain(pos.x + col, pos.y + row) == null) putTerrain(terrainSet, terrain, pos.x + col, pos.y + row, furnishers);  
    }); 
  }
  public void putTerrain(TerrainSet terrainSet, Terrain terrain, int x, int y, TerrainFurnishers furnishers) {
    terrainSet.putTerrain(terrain, x, y);
    if (furnishers != null) furnishers.furnish(terrainSet, roomsBuilder, terrain, x, y);
  }
  public void placeWalls(TerrainSet terrainSet, TerrainFurnishers furnishers) throws Exception {
    // Logger.log("Rooms before", )
    roomsBuilder.rooms.sort((a, b)-> (a.y > b.y) ? 1 : (b.y > a.y) ? -1 : 0 );
    for (Rectangle room : roomsBuilder.rooms) {
      int cols = (int)(room.width / data.tileSize);
      int rows = (int)(room.height / data.tileSize);
      // Go explore topmost and bottommost tile in room horizontally
      Pointi pos = roomsBuilder.getRoomRelTilePos(room);
      int top = pos.y;
      int bottom = pos.y + rows;
      int left = pos.x;
      int right = pos.x + cols - 1;
      // Go explore horizontally
      for (int col = 0; col < cols; col++) {
        Terrain terrain = walls[WallType.BODY].getVariant();
        // terrain.canTravel = false;
        if (terrainSet.getTerrain(pos.x + col, top - 1) == null) { 
          for (int i = 0; i < data.normalHeight-1; i++) putTerrain(terrainSet, terrain.clone(), pos.x + col, top - i, furnishers);
          putTerrain(terrainSet, walls[WallType.DOWN_EDGE].getVariant(), pos.x + col, top-data.normalHeight+1, furnishers);
          putTerrain(terrainSet, walls[WallType.TOP_HEAD].getVariant(), pos.x + col, top, furnishers);
        }
        if (terrainSet.getTerrain(pos.x + col, bottom + 1) == null) {
          for (int i = 0; i < data.normalHeight-1; i++) putTerrain(terrainSet, terrain.clone(), pos.x + col, bottom - i, furnishers);
          putTerrain(terrainSet, walls[WallType.DOWN_EDGE].getVariant(), pos.x + col, bottom-data.normalHeight+1, furnishers);
          putTerrain(terrainSet, walls[WallType.TOP_HEAD].getVariant(), pos.x + col, bottom, furnishers);
        }
        
      }
      
      // Go explore leftmost and rightmost tile vertiacally
      for (int row = 0; row < rows; row++) {
        Terrain leftWall = walls[WallType.LEFT_HEAD].getVariant();
        Terrain rightWall = walls[WallType.RIGHT_HEAD].getVariant();
        if (terrainSet.getTerrain(left - 1, pos.y + row) == null || (terrainSet.getTerrain(left, pos.y+row).type.contains("wall")) )
          putTerrain(terrainSet, leftWall, left, pos.y + row, furnishers);
        if (terrainSet.getTerrain(right + 1, pos.y + row) == null || (terrainSet.getTerrain(right, pos.y+row).type.contains("wall")) ) 
          putTerrain(terrainSet, rightWall, right, pos.y + row, furnishers);
      }
    }
  }

  // Call before styling anything (before placing any terrains based on created rooms)
  public static void combine(TerrainSet base, TerrainSet add, RoomsBuilder roomsBuilder, Rectangle replacedRoom) {
    roomsBuilder.rooms.remove(replacedRoom);
    Pointi replacedRoomPos = roomsBuilder.getRoomRelTilePos(replacedRoom);
    int rCols = (int)replacedRoom.width / roomsBuilder.tileSize;
    int rRows = (int)replacedRoom.height / roomsBuilder.tileSize;
    // Origin on center of the replaced room
    int rCenterX = replacedRoomPos.x + rCols / 2;
    int rCenterY = replacedRoomPos.y + rRows / 2;
    int startX = rCenterX - add.cols / 2;
    int startY = rCenterY - add.rows / 2;
    int endX = startX + add.cols;
    int endY = startY + add.rows;

    for (int x = startX; x < endX; x++) {
      for (int y = startY; y < endY; y++) {
        base.putTerrain(add.getTerrain(x-startX, y-startY).clone(), x, y);
      }
    }
    
  }
  public static TerrainSet blank() {
    return new TerrainSet(Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/32, 32, 32);
  }
  public static ArrayList<Obj> selectExits(TerrainSet terrainSet) {
    ArrayList<Obj> exits = new ArrayList<>();
    return exits;
  }
}
