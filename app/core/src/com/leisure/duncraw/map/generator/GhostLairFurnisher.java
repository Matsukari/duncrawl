package com.leisure.duncraw.map.generator;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.map.Decoration;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.map.TerrainSet;

import lib.math.Pointi;

public class GhostLairFurnisher extends TerrainFurnisher {
  public int minBody;
  public int maxBody;
  private Pointi nextOffset = new Pointi(100, 200);
  private int lastBody;
  private int lastOffset;
  @Override
  public void furnish(TerrainSet terrainSet, RoomsBuilder roomsBuilder, Terrain terrain, int x, int y) {
    if (lastBody >= lastOffset && terrain.type.contains("ground")) {
      String ranCorpse = Graphics.objsSources.corpses.get(MathUtils.random(Graphics.objsSources.corpses.size()-1));
      terrainSet.putObject(new Decoration(ranCorpse), x, y); 
      lastBody = 0;
      lastOffset = MathUtils.random(nextOffset.x, nextOffset.y);
    }
    lastBody++;
  }
  
}
