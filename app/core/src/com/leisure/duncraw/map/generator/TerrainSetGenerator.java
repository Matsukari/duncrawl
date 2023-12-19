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

public class TerrainSetGenerator {
  public RoomsBuilder roomsBuilder;
  public FloorData data;
  public ArrayList<Terrain> sideHeads = new ArrayList<>();
  public ArrayList<Terrain> topHeads = new ArrayList<>();
  public ArrayList<Terrain> grounds = new ArrayList<>();
  public ArrayList<Terrain> walls = new ArrayList<>();
  public static String headsOrder = "><-|";
  public TerrainSetGenerator(FloorData data) {
    this.data = data;
    roomsBuilder = new RoomsBuilder(data.tileSize);
  }
  public TerrainSet gen() {
    roomsBuilder.build(data.roomsNum, new Vector2(data.getMaxWidth(), data.getMaxHeight()), data.widthRange, data.heightRange);
    Rectangle floorRect = roomsBuilder.rect;
    floorRect.width *= 2;
    floorRect.height *= 2;
    TerrainSet terrainSet = new TerrainSet((int)(floorRect.width/data.tileSize), (int)(floorRect.height/data.tileSize), data.tileSize, data.tileSize);
    Logger.log("TerrainSetGenerator", String.format("Size of terrain be generated: %d %d", terrainSet.cols, terrainSet.rows));
    Logger.log("TerrainSetGenerator", floorRect.toString());
    ArrayList<Rectangle> expandedCorridors = roomsBuilder.expandCorridors(4, false);
    roomsBuilder.rooms.addAll(expandedCorridors);
    try { 
      placeGrounds(terrainSet); 
      placeWalls(terrainSet);
    }
    catch (Exception e) { gen(); }
    
    return terrainSet;
  }
  public void placeGrounds(TerrainSet terrainSet) throws Exception {
    roomsBuilder.forEachTileInRooms(roomsBuilder.rooms, (room, col, row)->{
      Vector2 pos = roomsBuilder.getRoomRelTilePos(room);
      Terrain terrain = grounds.get(MathUtils.random(0, grounds.size()-1)).clone();
      if ( pos.x < 0 || pos.y < 0 ) Logger.log("TerrainSetGenerator", "1");
      if ( col < 0 || row < 0 ) Logger.log("TerrainSetGenerator", "2");
      if ( pos.y + row < 0f ) Logger.log("TerrainSetGenerator", "3");
      if ( pos.x + col < 0f ) Logger.log("TerrainSetGenerator", "4");
      try { terrainSet.putTerrain(terrain, (int)pos.x + col, (int)pos.y + row); } 
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
        Vector2 pos = roomsBuilder.getRoomRelTilePos(room);
        int top = (int)pos.y;
        int bottom = (int)pos.y + rows;
        if (terrainSet.getTerrain((int)pos.x + col, top - 1) == null) terrainSet.putTerrain(terrain, (int)pos.x + col, top);
        if (terrainSet.getTerrain((int)pos.x + col, bottom + 1) == null) terrainSet.putTerrain(terrain, (int)pos.x + col, bottom);
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
