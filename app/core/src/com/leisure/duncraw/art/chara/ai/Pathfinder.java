package com.leisure.duncraw.art.chara.ai;

import java.util.ArrayList;
import java.util.HashMap;

import org.locationtech.jts.geom.Point;

import com.badlogic.gdx.utils.Queue;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.TerrainSet;

import lib.math.Pointi;

// A* algorithm
public class Pathfinder {
  private HashMap<Pointi, Pointi> root;
  private Pointi goal = null;
  private Pointi start = null;
  public Pathfinder(final TerrainSet terrainSet, final Pointi start, final Pointi goal) {
    assert start != null && goal != null;
    Queue<Pointi> frontier = new Queue<Pointi>();
    root = new HashMap<Pointi, Pointi>();
    frontier.addLast(start);
    Pointi current = frontier.first();

    while (frontier.notEmpty()) {
      current = frontier.removeFirst();

      if (current.equals(goal)) {
        // Logger.log("Pathfinder", "Reched goal");
        this.goal = goal;
        this.start = start;
        break;
      }
      ArrayList<Pointi> adjacents = getAdjacentCross(terrainSet, current.x, current.y);
      for (Pointi next : adjacents) {
        if ((!root.containsValue(next)) && terrainSet.getTerrain(next.x, next.y) != null) {
          frontier.addLast(next);
          root.put(next, current);
        }
      }  
    }
  }
  public static Pointi getImmediateDiagonal(Pointi start, Pointi dst) {

    int dX = start.x - dst.x;
    int dY = start.y - dst.y;
    Pointi step = new Pointi(0, 0);
    if (Math.abs(dX) > Math.abs(dY) && dX != 0) step.x = 1 * (dX/Math.abs(dX));
    else if (dY != 0) step.y = 1 * (dY/Math.abs(dY));

    return step;
  }
  public ArrayList<Pointi> getArrayPath() {
    ArrayList<Pointi> paths = new ArrayList<Pointi>();
    if (goal != null) {
      Pointi current = goal;
      while (!current.equals(start)) {
        paths.add(0, current);
        current = root.get(current);
      }
      paths.add(0, start);
    }
    return paths;
  }
  public Queue<Pointi> getQueuedPath() {
    Queue<Pointi> paths = new Queue<Pointi>();
    if (goal != null) {
      Pointi current = goal;
      while (!current.equals(start)) {
        paths.addLast(current);
        current = root.get(current);
      }
      paths.addLast(start);
    }
    return paths;
  }
  public static ArrayList<Pointi> getAdjacentCross(TerrainSet grid, int x, int y) {
    ArrayList<Pointi> result = new ArrayList<>();
    result.add(new Pointi(x+1, y));
    result.add(new Pointi(x-1, y));
    result.add(new Pointi(x, y+1));
    result.add(new Pointi(x, y-1));
    return result;
  }
} 
