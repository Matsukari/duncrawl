package com.leisure.duncraw.map.generator;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.map.Decoration;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.map.TerrainSet;

import lib.math.Pointi;

public class MiscDecorationFurnisher extends TerrainFurnisher {
  public int minDec;
  public int maxDec;
  private Pointi nextOffset = new Pointi(50, 100);
  private int lastDec;
  private int lastOffset;
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
}
