package com.leisure.duncraw.map.generator;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.map.LayeredTerrain;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.ObjParser;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.RenderSortManager;
import com.leisure.duncraw.map.TerrainSet;
import com.leisure.duncraw.map.TerrainVariants;
import com.leisure.duncraw.map.WallType;

import lib.math.Pointi;

public class TerrainSetGenerator {
  public RoomsBuilder roomsBuilder;
  public FloorData data;
  public TerrainSet terrainSet;
  public final TerrainFurnishers wallFurnishers = new TerrainFurnishers();
  public final TerrainFurnishers groundFurnishers = new TerrainFurnishers();
  public final RenderSortManager renderSortManager;
  public TerrainVariants walls[] = new TerrainVariants[8];
  public TerrainVariants grounds = new TerrainVariants();
  public String datFile;
  public TerrainSetGenerator(String datFile, FloorData data, RenderSortManager renderSortManager) {
    this.renderSortManager = renderSortManager;
    this.datFile = datFile;
    this.data = data;
    roomsBuilder = new RoomsBuilder(data.tileSize);
  }
  public TerrainSet prepare() {
    Logger.hide("RoomsBuilder"); 
    Logger.hide("TerrainSet");
    
    roomsBuilder = data.generation.roomsBuilder;
    if (data.firstGen) roomsBuilder.build(data.roomsNum, new Vector2(data.getMaxWidth(), data.getMaxHeight()), data.widthRange, data.heightRange);
    TerrainSet terrainSet = new TerrainSet(
        (int)(roomsBuilder.rect.width/data.tileSize)+2, 
        (int)(roomsBuilder.rect.height/data.tileSize)+2, 
        data.tileSize, data.tileSize,
        renderSortManager);
    Logger.log("TerrainSetGenerator", String.format("Size of terrain be generated: %d %d", terrainSet.cols, terrainSet.rows));
    // Logger.log("TerrainSetGenerator", roomsBuilder.rect.toString());
    if (data.firstGen) {
      ArrayList<Rectangle> expandedCorridors = roomsBuilder.expandCorridors(4, false);
      roomsBuilder.rooms.addAll(expandedCorridors);
    }
    
    return terrainSet;
  }
  public void populate(TerrainSet set) {
    terrainSet = set;
    try {
      placeGrounds(groundFurnishers);
      if (data.firstGen) {
        placeWalls(wallFurnishers);
        data.firstGen = false;
        for (Map.Entry<Pointi, Obj> obj : terrainSet.objs.data.entrySet()) {
          FloorData.Generation.Entity entity = new FloorData.Generation.Entity();
          entity.x = obj.getKey().x;
          entity.y = obj.getKey().y;
          entity.classname = obj.getValue().getClass().getSimpleName();
          entity.dat = obj.getValue().datFile;
          data.generation.entities.add(entity);
        } 
        Serializer.save(data, datFile);
      }
    } catch (Exception e) { 
      e.printStackTrace();
      System.exit(-1);
    }
  }
  public void placeGrounds(TerrainFurnishers furnishers) throws Exception {
    if (data.firstGen) groundFurnishers.start(terrainSet, roomsBuilder);
    roomsBuilder.forEachTileInRooms(roomsBuilder.rooms, (room, col, row)->{
      Pointi pos = roomsBuilder.getRoomRelTilePos(room);
      pos.x += 1;
      Terrain terrain = grounds.get(MathUtils.random(0, grounds.size()-1)).clone();
      if (terrainSet.getTerrain(pos.x + col, pos.y + row) == null) putTerrain(terrain, pos.x + col, pos.y + row, furnishers);  
    }); 
    if (data.firstGen) groundFurnishers.finish(terrainSet, roomsBuilder);
  }
  public void putTerrain(Terrain terrain, int x, int y, TerrainFurnishers furnishers) {
    terrainSet.putTerrain(terrain, x, y);
    if (furnishers != null && data.firstGen) furnishers.furnish(terrainSet, roomsBuilder, terrain, x, y);
    // Logger.log("TerrainSetGenerator", "Put terrain");
  }
  public void replaceTerrain(Terrain terrain, int x, int y, TerrainFurnishers furnishers) {
    terrainSet.replaceTerrain(terrain, x, y);
    if (furnishers != null && data.firstGen) furnishers.furnish(terrainSet, roomsBuilder, terrain, x, y);
  }
  public Pointi makeWall(LayeredTerrain group, int type, int x, int y, TerrainFurnishers furnishers, Color color) {
    Terrain terrain = walls[type].getVariant();
    replaceTerrain(terrain, x, y, furnishers);
    // Terrain obj = walls[type].getVariant();
    // terrain.tint = color;
    // terrain.bounds.setPosition(x * data.tileSize, y * data.tileSize);
    // terrain.bounds.setSize(data.tileSize);
    // group.add(terrain);
    return (new Pointi(x, y));
  }
  public Pointi makeWallA(LayeredTerrain group, int type, int x, int y, TerrainFurnishers furnishers) { return makeWall(group, type, x, y, furnishers,Color.RED); }
  public Pointi makeWallB(LayeredTerrain group, int type, int x, int y, TerrainFurnishers furnishers) { return makeWall(group, type, x, y, furnishers,Color.BLUE); }
  public void placeWalls(TerrainFurnishers furnishers) throws Exception {
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
        // terrain.canTravel = false // bottom;
        if (terrainSet.getTerrain(pos.x + col, top - 1) == null) { 
          LayeredTerrain wallGroup = new LayeredTerrain();
          for (int i = 0; i < data.normalHeight-1; i++) {
            wallBodies.add(makeWallA(wallGroup, WallType.BODY, pos.x + col, top - i, furnishers));
          }
          wallBodies.add(makeWallA(wallGroup, WallType.DOWN_EDGE, pos.x + col, top-data.normalHeight+1, furnishers));
        
          Terrain topHead = walls[WallType.TOP_HEAD].getVariant();
          putTerrain(topHead, pos.x + col, top, furnishers);
          wallGroup.add(topHead);
          // terrainSet.getTerrain(pos.x + col, top).setTravel(true);
          
          wallGroup.bounds.setPosition((float)(pos.x+col) * data.tileSize, (float)(top-data.normalHeight+1) * data.tileSize);
          terrainSet.putObject(wallGroup, pos.x + col, top-data.normalHeight-1, false);
        }
        if (terrainSet.getTerrain(pos.x + col, bottom + 1) == null) {
          LayeredTerrain wallGroup = new LayeredTerrain();
          for (int i = 0; i < data.normalHeight-1; i++) {
            wallBodies.add(makeWallB(wallGroup, WallType.BODY, pos.x + col, bottom - i, furnishers));
          }
          wallBodies.add(makeWallB(wallGroup, WallType.DOWN_EDGE, pos.x + col, bottom-data.normalHeight+1, furnishers));

          Terrain topHead = walls[WallType.TOP_HEAD].getVariant();
          putTerrain(topHead, pos.x + col, bottom, furnishers);
          wallGroup.add(topHead);
          // terrainSet.getTerrain(pos.x + col, bottom).setTravel(true);
          
          wallGroup.bounds.setPosition((float)(pos.x+col) * data.tileSize, (float)(bottom-data.normalHeight+1) * data.tileSize);
          terrainSet.putObject(wallGroup, pos.x+col, bottom-data.normalHeight+1, false);
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
  public static void combine(TerrainSet base, TerrainSet add, RoomsBuilder roomsBuilder, Pointi center) { 
    // roomsBuilder.rooms.remove(replacedRoom);
    // Pointi replacedRoomPos = roomsBuilder.getRoomRelTilePos(replacedRoom);
    // Origin on center of the replaced room
    int startX = center.x - add.cols / 2;
    int startY = center.y - add.rows / 2;
    int endX = startX + add.cols;
    int endY = startY + add.rows;

    for (int x = startX; x < endX; x++) {
      for (int y = startY; y < endY; y++) {
        base.replaceTerrain(add.getTerrain(x-startX, y-startY).clone(), x, y);
      }
    }
    
  }
  public static ArrayList<Obj> selectExits(TerrainSet terrainSet) {
    ArrayList<Obj> exits = new ArrayList<>();
    return exits;
  }
}
