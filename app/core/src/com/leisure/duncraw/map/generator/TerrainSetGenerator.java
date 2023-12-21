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

import lib.math.Pointi;

public class TerrainSetGenerator {
  public RoomsBuilder roomsBuilder;
  public FloorData data;
  public ArrayList<Terrain> sideHeads = new ArrayList<>();
  public ArrayList<Terrain> topHeads = new ArrayList<>();
  public ArrayList<Terrain> grounds = new ArrayList<>();
  public ArrayList<Terrain> walls = new ArrayList<>();
  public static String headsOrder = "><-|";
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
      if (genFails >= 10) 
        Logger.log("TerrainSetGenerator", "Error: generation repeated too much");
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
      for (int col = 0; col < cols; col++) {
        Terrain terrain = walls.get(MathUtils.random(0, walls.size()-1)).clone();
        Pointi pos = roomsBuilder.getRoomRelTilePos(room);
        int top = pos.y;
        int bottom = pos.y + rows;
        if (terrainSet.getTerrain(pos.x + col, top - 1) == null) terrainSet.putTerrain(terrain, pos.x + col, top);
        if (terrainSet.getTerrain(pos.x + col, bottom + 1) == null) terrainSet.putTerrain(terrain, pos.x + col, bottom);
      }
      for (int row = 0; row < rows; row++) {
        float rightX;
        float rightY;
        float leftX;
        float leftY;
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
