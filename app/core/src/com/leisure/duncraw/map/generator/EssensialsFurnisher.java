package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.map.Decoration;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.objs.Chest;
import com.leisure.duncraw.art.map.objs.Stair;
import com.leisure.duncraw.art.map.objs.Totem;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.TerrainSet;
import com.leisure.duncraw.map.TerrainVariants;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.screen.GameScreen.Context;

import lib.math.Pointi;

public class EssensialsFurnisher extends TerrainFurnisher {
  // By room
  public int totems = 4;
  public ArrayList<String> chests = new ArrayList<>();
  public ArrayList<Obj> stairs;
  private Floor floor;
  public EssensialsFurnisher(Floor floor, Tileset tileset, Context context) {
    this.floor = floor;
    stairs = tileset.objTransform(floor, context, tileset.filter("terrain", "stair"));
  }
  @Override
  public void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y) {
    if (genDistrib(50, 100, 0) && terrain.type.contains("ground")) {
      String ranCorpse = Graphics.objsSources.rocks.get(MathUtils.random(Graphics.objsSources.rocks.size()-1));
      terrainSet.putObject(new Decoration(ranCorpse), x, y); 
    }
  }
  @Override
  public void finish(TerrainSet terrainSet, RoomsBuilder roomsBuilder) {
    for (int i = 0; i < totems; i++) {
      Pointi ranPos = roomsBuilder.getRandomTileInRoom(roomsBuilder.mainRooms.get(i%(roomsBuilder.mainRooms.size()))); 
      terrainSet.putObject(new Totem(Graphics.objsSources.totems.get(MathUtils.random(Graphics.objsSources.totems.size()-1))), ranPos.x, ranPos.y);
    }
    for (int i = 0; i < chests.size(); i++) {
      Pointi ranPos = roomsBuilder.getRandomTileInRoom(roomsBuilder.mainRooms.get(i%(roomsBuilder.mainRooms.size()))); 
      terrainSet.putObject(new Chest(chests.get(i)), ranPos.x, ranPos.y);
    }
    
    Pointi upFloor = roomsBuilder.getRandomTileInMainRooms(); 
    Pointi downFloor = roomsBuilder.getRandomTileInMainRooms(); 
    terrainSet.putObject(stairs.get(0), downFloor.x, downFloor.y);
    terrainSet.putObject(stairs.get(1), upFloor.x, upFloor.y);
  }
}
