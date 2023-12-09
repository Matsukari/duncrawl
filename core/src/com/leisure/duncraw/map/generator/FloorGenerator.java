package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Room;
import com.leisure.duncraw.map.TerrainSet;

import lib.math.Edge;

public class FloorGenerator {
  public FloorData data;
  public ArrayList<Room> reqRooms;
  public RoomsBuilder roomsBuilder;
  public boolean consumeReq = true;
  // public static class Texture {
    public ArrayList<Terrain> heads = new ArrayList<>();
    public ArrayList<Terrain> grounds = new ArrayList<>();
    public ArrayList<TextureRegion> walls = new ArrayList<>();
    public static String headsOrder = "><-|";
  // }
  public FloorGenerator(FloorData data) {
    this.data = data;
    roomsBuilder = new RoomsBuilder(data.tileSize);
  }
  public Floor gen() {
    roomsBuilder.build(data.roomsNum, new Vector2(data.getMaxWidth(), data.getMaxHeight()), data.widthRange, data.heightRange);
    Rectangle floorRect = roomsBuilder.rect;
    floorRect.width *= 2;
    floorRect.height *= 2;
    TerrainSet terrainSet = new TerrainSet((int)(floorRect.width/data.tileSize), (int)(floorRect.height/data.tileSize), data.tileSize, data.tileSize);
    Logger.log("Size", String.format("%d %d", terrainSet.cols, terrainSet.rows));
    Logger.log("Rect", floorRect.toString());
    ArrayList<Rectangle> expandedCorridors = roomsBuilder.expandCorridors(4, false);
    roomsBuilder.rooms.addAll(expandedCorridors);
    try { placeGrounds(terrainSet); }
    catch (Exception e) { gen(); }
    
    return new Floor(terrainSet, terrainSet);
  }
  public void placeGrounds(TerrainSet terrainSet) throws Exception {
    roomsBuilder.forEachTileInRooms(roomsBuilder.rooms, (room, col, row)->{
      Vector2 pos = roomsBuilder.getRoomRelTilePos(room);
      Terrain terrain = grounds.get(0).clone();
      if ( pos.x < 0 || pos.y < 0 ) Logger.log("ERROR", "1");
      if ( col < 0 || row < 0 ) Logger.log("ERROR", "2");
      if ( pos.y + row < 0f ) Logger.log("ERROR", "3");
      if ( pos.x + col < 0f ) Logger.log("ERROR", "4");
      try { 
        terrainSet.putTerrain(terrain, 
          (int)pos.x + col, 
          ( ((int)pos.y + row))
          ); 
      } catch (Exception e) { throw new Exception(); }
    });
    
  }
}

/*    
     *      BASIC RECTANGULAR STRUCTURED INTERCONNECTED ROOMS
     *
     * Create outline of the whole floor
     * Determine main rooms
     * Connect paths from entrance to farthest node main room
     *
     *      ROOMS
     * 
     * Place wall texture based on depth of the walls on every edges of rooms
     *  * Place head texture on the topmost walls
     *  * If at corner on heads - place relevant texture (top-right, top-left, bottom-left, bottom-right)
     *  * Place relevant shadow overlay on the edges of walls texture
     * Place ground texture on every blocks surrounded by walls
     * Place relevant shadow overylay on edge corners
     *
     *
     * 
     *      OBJECTS
     *
     * Place a random object 
     *
     *
     *      CONNECTING THE ROOMS INTO ONE 
     *
     * Place prefabs next to random rooms such that two or more prefabs are distanced from each other
     * Pick a starting room where an Opening Stair obj is randomly placed
     * Pick an ending room where an Exit Stair obj is randomly placed
     * Connect all rooms into one and create an interconnected paths that leads to relevant rooms 
     *
     *      MAKING MINI MAP 
     * 
     * Create a framebuffer that you can render mini map 
     * Filter rooms to a minimum size that will be created a mini map 
     * Draw a rectangle on framebuffer for every selected rooms, with minimized size and position relative on a grid
     * The rendering will only be done in loading screen once and be rendered by other system components
     *
     * As for mob and charas representation
     *  * Place a circle on a minimized grid created using the position of the placer
     *
     * */

