package com.leisure.duncraw.debug;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.leisure.duncraw.art.chara.ai.Pathfinder;
import com.leisure.duncraw.screen.GameScreen;

import lib.math.Pointi;
import lib.tooling.ToolAgent;

public class PathfinderDebug extends ToolAgent {
  public GameScreen game;
  public ArrayList<Pointi> path;
  public PathfinderDebug(GameScreen game) {
    this.game =game;
    Pointi start = new Pointi(game.player.mapAgent.x, game.player.mapAgent.y);
    Pointi end = new Pointi(start);
    end.x += 1;
    end.y += 7;
    path = new Pathfinder(game.floorManager.getCurrentFloor().terrainSet, start, end).getArrayPath();
  }
  @Override
  public void render(ShapeRenderer renderer) {
    renderer.begin(ShapeType.Filled);
    for (int i = 0; i < path.size(); i++) {
      renderer.circle(game.player.mapAgent.x + path.get(i).x+i*32, game.player.mapAgent.y + path.get(i).y+i*32, 4);
    }
    renderer.end();
  } 
}
