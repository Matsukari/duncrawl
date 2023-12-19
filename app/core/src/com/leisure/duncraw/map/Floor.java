package com.leisure.duncraw.map;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.map.generator.FloorGenerator;
import com.leisure.duncraw.map.generator.RoomsBuilder;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

import lib.math.Pointi;

public class Floor extends Tilemap {
  public ArrayList<Obj> exits = new ArrayList<>();
  public TerrainSetGenerator generator;
  public EnemySpawner spawner;
  public Tilemap foreground;
  public RoomsBuilder meta; 
  public int level;
  public static class Exit {
    public Floor next;
    public Obj obj;
  }
  public Floor(TerrainSet terrainSet, TerrainSet foreground) {
    super(terrainSet);
    if (foreground != null) {
      assert terrainSet.terrainWidth != foreground.terrainWidth || terrainSet.terrainHeight != foreground.terrainHeight;
      this.foreground = new Tilemap(foreground);
    }
  }
  public Floor(TerrainSetGenerator generator) {
    super(generator.gen());
    this.generator = generator;
    meta = generator.roomsBuilder;
    exits.addAll(TerrainSetGenerator.selectExits(terrainSet));
  }
  public Pointi getTileInRandomRoom() {
    Pointi tile = new Pointi(0, 0);
    if (meta == null) return tile;
    Rectangle room = meta.rooms.get(MathUtils.random(meta.mainRooms.size()-1));
    tile.x = (int)(MathUtils.random(room.x, room.x + room.width) - meta.min.x) / terrainSet.terrainWidth;
    tile.y = (int)(MathUtils.random(room.y, room.y + room.height) - meta.min.y) / terrainSet.terrainHeight;
    if (!terrainSet.isWithin((int)tile.x, (int)tile.y)) getTileInRandomRoom();
    return tile;
  }
  public void renderForeground() {
    if (foreground != null) foreground.render();
  }
  public void update() {
  }
  public void initialSpawn(EnemySpawner spawner) {
    this.spawner = spawner;
  }
}
