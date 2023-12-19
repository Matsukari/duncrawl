package com.leisure.duncraw.art.chara.ai;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Queue;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.TerrainSet;

import lib.math.Pointi;

// A* algorithm
public class Pathfinder {
  private HashMap<Pointi, Pointi> root;
  private ArrayList<Pointi> pathResult;
  public Pathfinder(TerrainSet terrainSet, Pointi start, Pointi goal) {
    Queue<Pointi> frontier = new Queue<Pointi>();
    root = new HashMap<Pointi, Pointi>();
    frontier.addLast(start);
    Pointi current = frontier.first();

    while (frontier.notEmpty()) {
      current = frontier.removeFirst();

      if (current.equals(goal)) {
        Logger.log("Pathfinder", "Reched goal");
        break;
      }
      ArrayList<Terrain> adjacents = getAdjacentCross(terrainSet, current.x, current.y);
      Logger.log("Pathfinder", Integer.toString(adjacents.size()));
      for (Terrain nextTerrain : adjacents) {
        if (nextTerrain == null) continue;
        Pointi next = terrainSet.getTerrainMapCoord(nextTerrain);
        if ((!root.containsValue(next)) && terrainSet.getTerrain(next.x, next.y) != null) {
          frontier.addLast(next);
          root.put(next, current);
        }
      }  
    }
    if (current.equals(goal)) {
      ArrayList<Pointi> paths = new ArrayList<Pointi>();
      current = goal;
      while (current != start) {
        paths.add(current);
        current = root.get(current);
      }
      paths.add(start);
      pathResult = paths;

      Gdx.app.log("Root", root.toString());
      Gdx.app.log("Goal root", root.get(goal).toString());
    }
  }
  public ArrayList<Pointi> getPath() {
    return pathResult;
  }
  public static ArrayList<Terrain> getAdjacentCross(TerrainSet grid, int x, int y) {
    ArrayList<Terrain> result = new ArrayList<Terrain>();
    result.add(grid.getTerrain(x+1, y));
    result.add(grid.getTerrain(x-1, y));
    result.add(grid.getTerrain(x, y+1));
    result.add(grid.getTerrain(x, y-1));
    return result;
  }
} 
