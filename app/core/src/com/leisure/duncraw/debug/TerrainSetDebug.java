package com.leisure.duncraw.debug;

import java.io.File;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.map.TerrainSet;

import lib.tooling.ToolAgent;

public class TerrainSetDebug extends ToolAgent {
  public TerrainSet terrainSet;
  public TerrainSetDebug(TerrainSet terrainSet) {
    this.terrainSet = terrainSet;
  }
  @Override
  public void render(ShapeRenderer renderer) {
    renderer.begin(ShapeType.Filled);
    for (int i = 0; i < terrainSet.terrains.length; i++) {
      Terrain terrain = terrainSet.terrains[i];
      if (terrain != null) renderer.rect(i%terrainSet.cols, i/terrainSet.cols, terrainSet.terrainWidth, terrainSet.terrainHeight);
    }
    renderer.end();
  } 
}
