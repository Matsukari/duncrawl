package com.leisure.duncraw.map.generator;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.map.Decoration;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.objs.Chest;
import com.leisure.duncraw.art.map.objs.Totem;
import com.leisure.duncraw.map.TerrainSet;

import lib.math.Pointi;

public class MiscDecorationFurnisher extends TerrainFurnisher {
  public int minDec;
  public int maxDec;
  private Pointi nextOffset = new Pointi(50, 100);
  private int lastDec;
  private int lastOffset;
  // By room
  public int totems = 4;
  public ArrayList<String> chests = new ArrayList<>();
  @Override
  public void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y) {
    if (lastDec >= lastOffset && terrain.type.contains("ground")) {
      String ranCorpse = Graphics.objsSources.rocks.get(MathUtils.random(Graphics.objsSources.rocks.size()-1));
      terrainSet.putObject(new Decoration(ranCorpse), x, y); 
      lastDec = 0;
      lastOffset = MathUtils.random(nextOffset.x, nextOffset.y);
    }
    lastDec++;
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
  }
}
