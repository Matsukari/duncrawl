package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.map.generator.RoomsBuilder;

public class Floor extends Tilemap {
  public ArrayList<Obj> exits = new ArrayList<>();
  public RoomsBuilder meta; 
  public Tilemap foreground; 
  public int level;
  public static class Exit {
    public Floor next;
    public Obj obj;
  }
  public Floor(TerrainSet terrainSet, TerrainSet foreground) { 
    super(terrainSet);
    assert terrainSet.terrainWidth != foreground.terrainWidth || terrainSet.terrainHeight != foreground.terrainHeight;
    this.foreground = new Tilemap(foreground);
  }
  public Vector2 getTileInRandomRoom() {
    if (meta == null) return Vector2.Zero;
    Vector2 tile = new Vector2();
    Rectangle room = meta.mainRooms.get(MathUtils.random(meta.mainRooms.size()-1));
    tile.x = (int)(MathUtils.random(room.x, room.x + room.width) - meta.min.x) / terrainSet.terrainWidth;
    tile.y = (int)(MathUtils.random(room.y, room.y + room.height) - meta.min.y) / terrainSet.terrainHeight;
    return tile;
  }
  public void renderForeground() {
    foreground.render();
  }
  public void genLights() {
  }
}
