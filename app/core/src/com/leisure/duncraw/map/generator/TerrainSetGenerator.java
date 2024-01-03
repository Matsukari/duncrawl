package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.map.LayeredTerrain;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.RenderSortManager;
import com.leisure.duncraw.map.TerrainSet;
import com.leisure.duncraw.map.TerrainVariants;
import com.leisure.duncraw.map.WallType;

import lib.math.Pointi;

public class TerrainSetGenerator {
  public RoomsBuilder roomsBuilder;
  public FloorData data;
  public final TerrainFurnishers wallFurnishers = new TerrainFurnishers();
  public final TerrainFurnishers groundFurnishers = new TerrainFurnishers();
  public final RenderSortManager renderSortManager;
  public TerrainVariants walls[] = new TerrainVariants[8];
  public TerrainVariants grounds = new TerrainVariants();
  public TerrainSetGenerator(FloorData data, RenderSortManager renderSortManager) {
    this.renderSortManager = renderSortManager;
    this.data = data;
    roomsBuilder = new RoomsBuilder(data.tileSize);
  }
  public TerrainSet prepare() {
    roomsBuilder.build(data.roomsNum, new Vector2(data.getMaxWidth(), data.getMaxHeight()), data.widthRange, data.heightRange);
    TerrainSet terrainSet = new TerrainSet(
        (int)(roomsBuilder.rect.width/data.tileSize)+2, 
        (int)(roomsBuilder.rect.height/data.tileSize)+2, 
        data.tileSize, data.tileSize,
        renderSortManager);
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
    groundFurnishers.start(terrainSet, roomsBuilder);
    roomsBuilder.forEachTileInRooms(roomsBuilder.rooms, (room, col, row)->{
      Pointi pos = roomsBuilder.getRoomRelTilePos(room);
      pos.x += 1;
      Terrain terrain = grounds.get(MathUtils.random(0, grounds.size()-1)).clone();
      if (terrainSet.getTerrain(pos.x + col, pos.y + row) == null) putTerrain(terrainSet, terrain, pos.x + col, pos.y + row, furnishers);  
    }); 
    groundFurnishers.finish(terrainSet, roomsBuilder);
  }
  public void putTerrain(TerrainSet terrainSet, Terrain terrain, int x, int y, TerrainFurnishers furnishers) {
    terrainSet.putTerrain(terrain, x, y);
    if (furnishers != null) furnishers.furnish(terrainSet, roomsBuilder, terrain, x, y);
    // Logger.log("TerrainSetGenerator", "Put terrain");
  }
  public void replaceTerrain(TerrainSet terrainSet, Terrain terrain, int x, int y, TerrainFurnishers furnishers) {
    terrainSet.replaceTerrain(terrain, x, y);
    if (furnishers != null) furnishers.furnish(terrainSet, roomsBuilder, terrain, x, y);
  }
  public void placeWalls(TerrainSet terrainSet, TerrainFurnishers furnishers) throws Exception {
    // Logger.log("Rooms before", )
    wallFurnishers.start(terrainSet, roomsBuilder);
    roomsBuilder.rooms.sort((a, b)-> (a.y > b.y) ? 1 : (b.y > a.y) ? -1 : 0 );
    ArrayList<Pointi> wallBodies = new ArrayList<>();
    for (Rectangle room : roomsBuilder.rooms) {
      int cols = (int)(room.width / data.tileSize);
      int rows = (int)(room.height / data.tileSize);
      // Go explore topmost and bottommost tile in room horizontally
      Pointi pos = roomsBuilder.getRoomRelTilePos(room);
      pos.x += 1;
      int top = pos.y;
      int bottom = pos.y + rows;
      // Go explore horizontally
      for (int col = 0; col < cols; col++) {
        Terrain terrain = walls[WallType.BODY].getVariant();
        // terrain.canTravel = false;
        if (terrainSet.getTerrain(pos.x + col, top - 1) == null) { 
          for (int i = 0; i < data.normalHeight-1; i++) {
            replaceTerrain(terrainSet, terrain.clone(), pos.x + col, top - i, furnishers);
            wallBodies.add(new Pointi(pos.x+col, top-i));
          }
          replaceTerrain(terrainSet, walls[WallType.DOWN_EDGE].getVariant(), pos.x + col, top-data.normalHeight+1, furnishers);
          wallBodies.add(new Pointi(pos.x+col, top-data.normalHeight+1));
          Terrain topHead = walls[WallType.TOP_HEAD].getVariant();

          putTerrain(terrainSet, topHead, pos.x + col, top, furnishers);
          terrainSet.getTerrain(pos.x + col, top).setTravel(true);
        }
        if (terrainSet.getTerrain(pos.x + col, bottom + 1) == null) {
          for (int i = 0; i < data.normalHeight-1; i++) {
            replaceTerrain(terrainSet, terrain.clone(), pos.x + col, bottom - i, furnishers);
            wallBodies.add(new Pointi(pos.x+col, bottom-i));
          }
          replaceTerrain(terrainSet, walls[WallType.DOWN_EDGE].getVariant(), pos.x + col, bottom-data.normalHeight+1, furnishers);
          wallBodies.add(new Pointi(pos.x+col, bottom-data.normalHeight+1));
          putTerrain(terrainSet, walls[WallType.TOP_HEAD].getVariant(), pos.x + col, bottom, furnishers);
          terrainSet.getTerrain(pos.x + col, bottom).setTravel(true);
        }
        
      }
      
    }
    wallFurnishers.finish(terrainSet, roomsBuilder);
    // Place side heads
    ArrayList<Pointi> sideHeads = new ArrayList<>();
    for (Pointi body : wallBodies) {
      int x = body.x;
      int y = body.y;
      // Go explore leftmost and rightmost tile vertiacally
      Terrain sideWall = null;
      Terrain left = terrainSet.getTerrain(x-1, y);
      Terrain right = terrainSet.getTerrain(x+1, y);
      // Logger.log("TerrainSetGenerator", SString.toString(body.bounds));
      
      if (left == null || terrainTypeHas("ground", left)) sideWall = walls[WallType.LEFT_HEAD].getVariant();
      if (right == null || terrainTypeHas("ground", right)) sideWall = walls[WallType.RIGHT_HEAD].getVariant();
      if (sideWall != null) {
        terrainSet.putTerrain(sideWall, x, y);
        sideHeads.add(new Pointi(x, y));
        Terrain bottom = null;
        while (y < terrainSet.rows) {
          y++;
          bottom = terrainSet.getTerrain(x, y);
          left = terrainSet.getTerrain(x-1, y);
          right = terrainSet.getTerrain(x+1, y);
          sideWall = null;
          if (bottom == null) {
            if (left != null && (terrainTypeHas("left", left) || terrainTypeHas("right", left))) break;
            if (right != null && (terrainTypeHas("left", right) || terrainTypeHas("right", right))) break;
            if (left != null && terrainTypeHas("ground", left)) sideWall = walls[WallType.LEFT_HEAD].getVariant();
            if (right != null && terrainTypeHas("ground", right)) sideWall = walls[WallType.RIGHT_HEAD].getVariant();
          }
          else {
            // Already populated; only take the bottomost unit of a set of wall
            if (terrainTypeHas("left", bottom) || terrainTypeHas("right", bottom)) break;
            if (left == null && terrainTypeHas("ground", bottom)) sideWall = walls[WallType.LEFT_HEAD].getVariant();
            if (right == null && terrainTypeHas("ground", bottom)) sideWall = walls[WallType.RIGHT_HEAD].getVariant();
          }
          if (sideWall != null) {
            terrainSet.putTerrain(sideWall, x, y);
            sideHeads.add(new Pointi(x, y));
          }
        }
      }
    }

  }
  private boolean terrainTypeHas(String type, Terrain terrain) {
    if (terrain instanceof LayeredTerrain) return ((LayeredTerrain)terrain).containsWType(type);
    return terrain.type.contains(type);
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
  public static ArrayList<Obj> selectExits(TerrainSet terrainSet) {
    ArrayList<Obj> exits = new ArrayList<>();
    return exits;
  }
}
