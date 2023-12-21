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
  // 0-5 for a grid representation of the body of a wall
  // 0 1 2 
  // 3 4 5
  // Other oddity collections
  // 6 7 
  public TerrainVariants walls[] = new TerrainVariants[8];
  public TerrainVariants grounds = new TerrainVariants();
  public int genFails = 0;
  public TerrainSetGenerator(FloorData data) {
    this.data = data;
    roomsBuilder = new RoomsBuilder(data.tileSize);
  }
  public TerrainSet gen() {
    roomsBuilder.build(data.roomsNum, new Vector2(data.getMaxWidth(), data.getMaxHeight()), data.widthRange, data.heightRange);
    TerrainSet terrainSet = new TerrainSet(
        (int)(roomsBuilder.rect.width/data.tileSize)+1, 
        (int)(roomsBuilder.rect.height/data.tileSize)+1, 
        data.tileSize, data.tileSize);
    Logger.log("TerrainSetGenerator", String.format("Size of terrain be generated: %d %d", terrainSet.cols, terrainSet.rows));
    Logger.log("TerrainSetGenerator", roomsBuilder.rect.toString());
    ArrayList<Rectangle> expandedCorridors = roomsBuilder.expandCorridors(4, false);
    roomsBuilder.rooms.addAll(expandedCorridors);
    try { 
      placeGrounds(terrainSet); 
      placeWalls(terrainSet);
    } catch (Exception e) { 
      if (genFails >= 10) {
        e.printStackTrace();
        Logger.log("TerrainSetGenerator", "Error: generation repeated too much");
        System.exit(-1);
      }
      genFails++; 
      gen(); 
    }
    
    return terrainSet;
  }
  public void placeGrounds(TerrainSet terrainSet) throws Exception {
    roomsBuilder.forEachTileInRooms(roomsBuilder.rooms, (room, col, row)->{
      Pointi pos = roomsBuilder.getRoomRelTilePos(room);
      Terrain terrain = grounds.get(MathUtils.random(0, grounds.size()-1)).clone();
      try { terrainSet.putTerrain(terrain, pos.x + col, pos.y + row); } 
      catch (Exception e) { throw new Exception(); }
    }); 
  }
  public void placeWalls(TerrainSet terrainSet) throws Exception {
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
        if (terrainSet.getTerrain(pos.x + col, top - 1) == null) { 
          for (int i = 0; i < data.normalHeight; i++) terrainSet.putTerrain(terrain, pos.x + col, top - i);
          terrainSet.putTerrain(walls[WallType.TOP_HEAD].getVariant(), pos.x + col, top - data.normalHeight + 1);
        }
        if (terrainSet.getTerrain(pos.x + col, bottom + 1) == null) {
          for (int i = 0; i < data.normalHeight; i++) terrainSet.putTerrain(terrain, pos.x + col, bottom - i);
          terrainSet.putTerrain(walls[WallType.TOP_HEAD].getVariant(), pos.x + col, bottom - data.normalHeight + 1);
        }
        
      } 
      // Go explore leftmost and rightmost tile vertiacally
      for (int row = 0; row < rows; row++) {
        Terrain leftWall = walls[WallType.LEFT_HEAD].getVariant();
        Terrain rightWall = walls[WallType.RIGHT_HEAD].getVariant();
        if (terrainSet.getTerrain(left - 1, pos.y + row) == null) terrainSet.putTerrain(leftWall, left, pos.y + row);
        if (terrainSet.getTerrain(right + 1, pos.y + row) == null) terrainSet.putTerrain(rightWall, right, pos.y + row);
      }
    }
  }

  public static void combine(TerrainSet base, TerrainSet add) {
    
  }
  public static TerrainSet blank() {
    return new TerrainSet(Gdx.graphics.getWidth()/32, Gdx.graphics.getHeight()/32, 32, 32);
  }
  public static ArrayList<Obj> selectExits(TerrainSet terrainSet) {
    ArrayList<Obj> exits = new ArrayList<>();
    return exits;
  }
}
