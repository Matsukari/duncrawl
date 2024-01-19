package com.leisure.duncraw.map.generator;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.art.map.LayeredTerrain;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.ObjParser;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.helper.Pair;
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

    // data.firstGen = true;
    
    roomsBuilder = data.generation.roomsBuilder;
    if (data.firstGen) roomsBuilder.build(data.roomsNum, new Vector2(data.getMaxWidth(), data.getMaxHeight()), data.widthRange, data.heightRange);
    TerrainSet terrainSet = new TerrainSet(
        (int)(roomsBuilder.rect.width/data.tileSize)+2, 
        (int)(roomsBuilder.rect.height/data.tileSize)+2+data.normalHeight, 
        data.tileSize, data.tileSize,
        renderSortManager);
    Logger.log("TerrainSetGenerator", String.format("Size of terrain be generated: %d %d", terrainSet.cols, terrainSet.rows));
    // Logger.log("TerrainSetGenerator", roomsBuilder.rect.toString());
    if (data.firstGen) {
      ArrayList<Rectangle> expandedCorridors = roomsBuilder.expandCorridors(4, false);
      roomsBuilder.rooms.addAll(expandedCorridors);
    }
    // Logger.log("TerrainSetGenerator", "First generation? " + Boolean.toString(data.firstGen));
    
    return terrainSet;
  }
  public void populate(TerrainSet set) {
    terrainSet = set;
    try {
      placeGrounds(groundFurnishers);
      placeWalls(wallFurnishers);
    } catch (Exception e) { 
      e.printStackTrace();
      System.exit(-1);
    }
    if (data.firstGen) {
      Logger.log("TerrainSetGenerator", "Saving genereted objects...");
      data.firstGen = false;
      for (Map.Entry<Pointi, Obj> obj : terrainSet.objs.data.entrySet()) {
        FloorData.Generation.Entity entity = new FloorData.Generation.Entity();
        if (obj.getValue() instanceof Terrain || obj.getValue() instanceof Item) continue;
        entity.x = (int)obj.getValue().bounds.x / data.tileSize;
        entity.y = (int)obj.getValue().bounds.y / data.tileSize;
        entity.classname = obj.getValue().getClass().getSimpleName();
        entity.dat = obj.getValue().datFile;
        data.generation.entities.add(entity);
      } 
      Serializer.save(data, datFile);
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
  private void vertiWallGroup(ArrayList<Pointi> wallBodies, int x, int start, TerrainFurnishers furnishers) {
    int height = data.normalHeight-1;
    LayeredTerrain wallGroup = new LayeredTerrain();
    for (int i = 0; i <= height; i++) wallBodies.add(makeWallA(wallGroup, WallType.BODY, x, start + i, furnishers));
    wallBodies.add(makeWallA(wallGroup, WallType.DOWN_EDGE, x, start, furnishers));

    // terrainSet.getTerrain(pos.x + col, top).setTravel(true);
    Terrain topHead = getWall(WallType.TOP_HEAD);
    putTerrain(topHead, x, start+height, furnishers);
    wallGroup.add(topHead);

    wallGroup.bounds.setPosition((float)(x) * data.tileSize, (float)(start) * data.tileSize);
    terrainSet.putObject(wallGroup, x, start, false);
  }
  public void placeWalls(TerrainFurnishers furnishers) throws Exception {
    // Logger.log("Rooms before", )
    if (data.firstGen) wallFurnishers.start(terrainSet, roomsBuilder);
    // roomsBuilder.rooms.sort((a, b)-> (a.y > b.y) ? 1 : (b.y > a.y) ? -1 : 0 );
    ArrayList<Pointi> wallBodies = new ArrayList<>();
    for (Rectangle room : roomsBuilder.rooms) {
      int cols = (int)(room.width / data.tileSize);
      int rows = (int)(room.height / data.tileSize);
      // Go explore topmost and bottommost tile in room horizontally
      Pointi pos = roomsBuilder.getRoomRelTilePos(room);
      pos.x += 1;
      pos.y += 0;
      int bottom = pos.y;
      int top = pos.y + rows;
      // Go explore horizontally, 
      for (int col = 0; col < cols; col++) {
        int x = pos.x + col;
        if (terrainSet.getTerrain(x, bottom - 1) == null) vertiWallGroup(wallBodies, x, bottom, furnishers);
        if (terrainSet.getTerrain(x, top + 1) == null) vertiWallGroup(wallBodies, x, top, furnishers); 
      }
      
    }
    if (data.firstGen) wallFurnishers.finish(terrainSet, roomsBuilder);
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
      
      if (left == null || terrainTypeHas("ground", left)) sideWall = getWall(WallType.LEFT_HEAD);
      if (right == null || terrainTypeHas("ground", right)) sideWall = getWall(WallType.RIGHT_HEAD);
      if (sideWall != null) {
        Pair<Integer, Integer> dig = digDownHasWall(x, y);
        // if (dig.a) terrainSet.putTerrain(sideWall, x, y);
        // if (dig.a || !dig.a) {
          // y -= data.normalHeight;
          for (int i = 0; i < dig.b; i++) {
            int offset = 0;
            if (dig.a == 0) {
              if (terrainTypeHas("left", sideWall)) sideWall = getWall(WallType.LEFT_HEAD);
              else sideWall = getWall(WallType.RIGHT_HEAD);
              // offset = 0;
            }
            else if (dig.a == 1) {
              sideWall = getWall(WallType.LEFT_HEAD); 
              // offset = 1;
            }
            else if (dig.a == -1) { 
              sideWall = getWall(WallType.LEFT_HEAD); 
            }
            else if (dig.a == 2) {
              sideWall = getWall(WallType.RIGHT_HEAD);
              // offset = -1;
            }
            else if (dig.a == -2) {
              sideWall = getWall(WallType.RIGHT_HEAD);
            }
            // Terrain d = getWall(WallType.LEFT_HEAD);
            // d.tint = Color.RED;
            terrainSet.putTerrain(sideWall, x+offset, y-i);
          }

        // }
      }
    }

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
    return (new Pointi(x, y));
  }
  public Pointi makeWallA(LayeredTerrain group, int type, int x, int y, TerrainFurnishers furnishers) { 
    return makeWall(group, type, x, y, furnishers,Color.RED); 
  }
  public Pointi makeWallB(LayeredTerrain group, int type, int x, int y, TerrainFurnishers furnishers) { 
    return makeWall(group, type, x, y, furnishers,Color.BLUE); 
  }
  private Terrain getWall(int type) {
    return walls[type].getVariant();
  }
  // It SHOULD be guaranteed that digging down below the afore wall will yield a subsequent wall since 2 wallblock exists within a box room (top & bottom). 
  private Pair<Integer, Integer> digDownHasWall(int x, int y) {
    Terrain start = terrainSet.getTerrain(x, y);
    int touchedNull = 1;
    if (terrainTypeHas("left_wall", start) || terrainTypeHas("right_wall", start)) return new Pair<>(0, 0);
    for (int i = 0; y >= 0; i++) {
      Terrain left = terrainSet.getTerrain(x-1, y);
      Terrain right = terrainSet.getTerrain(x+1, y);
      Terrain current = terrainSet.getTerrain(x, y);
      y--;
      if (current == null && left == null && right == null) return new Pair<>(0, 0);
      else if (terrainTypeHas("top_wall", current)) return new Pair<>(0, i+1);
      else if (terrainTypeHas("top_wall", left) && (!terrainTypeHas("ground", right) || terrainTypeHas("ground", current))) return new Pair<>(1*touchedNull, i+1);
      else if (terrainTypeHas("top_wall", right) && (!terrainTypeHas("ground", left) || terrainTypeHas("ground", current))) return new Pair<>(2*touchedNull, i+1);
      else if (current == null) {
        touchedNull = -1;
        continue;
      }
      else if ((current != null && left != null && right != null) && !terrainTypeHas("wall", current)) return new Pair<>(0, 0);
    }
    return new Pair<>(0, 0);

  }
  private boolean terrainTypeHas(String type, Terrain terrain) {
    if (terrain == null) return false;
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
