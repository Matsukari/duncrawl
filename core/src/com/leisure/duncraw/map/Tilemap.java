package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;

public class Tilemap {
  // columns and row
  public int rows = 0, cols = 0;
  public int terrainWidth = 32, terrainHeight = 32;
  private Terrain terrains[] = {};
  private ArrayList<Chara> chars = new ArrayList<>();
  public Tilemap(int cols, int rows, int terrainWidth, int terrainHeight) { 
    this.cols = cols;
    this.rows  = rows;
    this.terrainWidth = terrainWidth;
    this.terrainHeight = terrainHeight;
    terrains = new Terrain[cols * rows];
    for (int i = 0; i < cols * rows; i++) terrains[i] = null;
  }
  public void render() {
    for (Terrain terrain : terrains) terrain.render();
  }
  public void putTerrain(Terrain terrain, int x, int y) {
    Logger.log("Tilemap", "put terrain");
    terrains[y * cols + x] = terrain;
    terrain.bounds.x = x * terrainWidth;
    terrain.bounds.y = y * terrainHeight;
  }
  public void putObject(Terrain terrain, int x, int y) {
    Logger.log("Tilemap", "put obj");
  }
}
