package com.leisure.duncraw.manager;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.debug.CharaDebug;
import com.leisure.duncraw.debug.GridLines;
import com.leisure.duncraw.debug.PlayerDebug;
import com.leisure.duncraw.debug.SystemDebug;
import com.leisure.duncraw.debug.editor.FloorEditor;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

import lib.tooling.ToolAgent;
import lib.tooling.Tooling;

public class DebugManager extends ToolAgent {
  public ShapeRenderer renderer;
  public GridLines gridLines; 
  public Floor floor;
  public Tooling tooling;
  public DebugManager() {
    renderer = new ShapeRenderer();
    tooling = new Tooling();
    Tooling.init(tooling);
  }
  public void debugPlayer(Player player) {
    Tooling.addAgent(new PlayerDebug(player));
  }
  public void editFloorGen(FloorManager floorGenerator) {
    Tooling.addAgent(new FloorEditor(floorGenerator, renderer));  
  }
  public void debugChara(Chara chara) {
    Tooling.addAgent(new CharaDebug(chara));
  }
  public void debugMap(Floor floor) {
    Logger.log("DebugManager", "Debugging map");
    gridLines = new GridLines(floor.terrainSet.cols, floor.terrainSet.rows, 
      floor.terrainSet.terrainWidth, floor.terrainSet.terrainHeight);
  }
  public void debugSystem() {
    Tooling.addAgent(new SystemDebug());
  }
  public void render(Camera cam) { 
    renderer.setProjectionMatrix(cam.combined);
    if (gridLines != null) gridLines.render(renderer);
    tooling.render();
  }
  public void dispose() {
    tooling.dispose();
  }
}
