package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.leisure.duncraw.debug.GridLines;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

import lib.tooling.Tooling;

public class DebugManager {
  public ShapeRenderer renderer;
  public GridLines gridLines; 
  public Floor floor;
  public Tooling tooling;
  public DebugManager() {
    renderer = new ShapeRenderer();
    tooling = new Tooling();
    Tooling.init(tooling);
    Tooling.addAgent(new Logger());
  }
  public void debugMap(Floor floor) {
    Logger.log("DebugManager", "Debugging map");
    gridLines = new GridLines(floor.terrainSet.cols, floor.terrainSet.rows, 
      floor.terrainSet.terrainWidth, floor.terrainSet.terrainHeight);
  }
  public void render() { 
    if (gridLines != null) gridLines.render(renderer);
    // tooling.render();
  }
  public void dispose() {
    tooling.dispose();
  }
}
