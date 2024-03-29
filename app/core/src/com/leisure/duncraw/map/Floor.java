package com.leisure.duncraw.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.leisure.duncraw.art.chara.Spawner;
import com.leisure.duncraw.art.Art;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.map.Decoration;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.ObjParser;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.art.map.objs.Stair;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.data.Serializer;
import com.leisure.duncraw.helper.AArray;
import com.leisure.duncraw.helper.IdGenerator;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;
import com.leisure.duncraw.screen.GameScreen.Context;

import lib.math.Pointi;

public class Floor {
  public final ArrayList<TilemapChara> chars = new ArrayList<>();
  public final TerrainSetGenerator generator;
  public LightEnvironment lightEnvironment;
  public TerrainSet background;
  public TerrainSet foreground;
  public Context context;;
  public Spawner spawner;
  public Tileset tileset;
  public Player player;
  public int id;
  public int nextLevel = -1;
  public int prevFloor = -1;
  {
    id = IdGenerator.gen();
  }
  protected Floor(TerrainSetGenerator generator, boolean prepare) {
    this.generator = generator;
  }
  public Floor(TerrainSetGenerator generator) {
    this.generator = generator;
    setTerrainSet(generator.prepare()); 
  }
  public void stage(Player player, Tileset tileset, Context context) {
    this.player = player;
    this.context = context;
    this.tileset = tileset;
    onStage();
    generator.populate(background);
    loadGeneration();
    for (Map.Entry<Pointi, Obj> obj : background.objs.data.entrySet()) {
      obj.getValue().onStage(this);
    }
  }
  private void loadGeneration() {
    Logger.hide("Deserializer");
    ObjParser objParser = new ObjParser(this, context);
    HashMap<String, Integer> eLog = new HashMap<>();
    if (!generator.data.firstGen && generator.data.generation != null) {
      for (FloorData.Generation.Entity entity : generator.data.generation.entities) {
        Obj obj = objParser.from(entity.classname, entity.dat);
        String key = obj.getClass().getSimpleName();
        if (!eLog.containsKey(key)) eLog.put(key, Integer.valueOf(0));
        background.putObject(obj, entity.x, entity.y);
        eLog.put(key, Integer.valueOf(eLog.get(key).intValue() + 1));
      }
    }
    Logger.show("Deserializer");
    Logger.log("Floor", "Loaded generation: " + SString.toString(eLog));
  }
  public void unstage() {
    Serializer.save(generator.data, generator.datFile);
    for (TilemapChara agent : chars) {
      agent.chara.dropObj = null;
      if (!(agent.chara instanceof Player)) agent.chara.kill();
    }
    for (Map.Entry<Pointi, Obj> obj : background.objs.data.entrySet()) {
      obj.getValue().onUnstage(this);
    }
    background.objs.clear();
    nextLevel = -1;
    onUnstage();
    lightEnvironment.dispose();
  }
  public void render(SpriteBatch batch, TerrainSet layer) {
    for (Terrain terrain : layer.terrains) {
      if (terrain != null) terrain.render(batch);
    }
  }
  public void putChara(TilemapChara chara) {
    if (chars.contains(chara));
    chars.add(chara);
  }
  public TilemapChara getChara(int x, int y) {
    for (TilemapChara chara : chars) {
      for (int col = 0; col < chara.chara.dat.size.x; col++) {
        for (int row = 0; row < chara.chara.dat.size.y; row++) {
          if (chara.onBlock(x-col, y-row) && !chara.chara.status.dead) return chara;

        }
      }
    }
    return null;
  }
  protected void setTerrainSet(TerrainSet terrainSet) {
    background = terrainSet;
    lightEnvironment = new LightEnvironment(generator.data.envColor, new Rectangle(0, 0, background.getWidth(), background.getHeight()));
  }
  public void initialSpawn(Spawner spawner) { this.spawner = spawner; } 
  public boolean canTravel(int x, int y) {
    Terrain terrain = background.getTerrain(x, y);
    Obj obj = background.getObj(x, y);
    TilemapChara chara = getChara(x, y);
    // TilemapChara chara = getChara(x+player.movement.lastVelX, y+player.lastVelX);
    boolean traversable = true;
    if (terrain != null && !terrain.traversable()) traversable = false;
    if (obj != null && !(obj instanceof Item) && !(obj instanceof Decoration) && !(obj instanceof Terrain)) traversable = false;
    if (chara != null) traversable = false;
    return traversable;
  }
  public Pointi getTileInRandomRoom() {
    Pointi tile = new Pointi(0, 0);
    if (generator == null) return tile;
    Rectangle room = generator.roomsBuilder.rooms.get(MathUtils.random(generator.roomsBuilder.rooms.size()-1));
    tile.x = (int)(MathUtils.random(room.x, room.x + room.width) - generator.roomsBuilder.min.x) / background.terrainWidth;
    tile.y = (int)(MathUtils.random(room.y, room.y + room.height) - generator.roomsBuilder.min.y) / background.terrainHeight;
    if (!background.isWithin((int)tile.x, (int)tile.y)) getTileInRandomRoom();
    return tile;
  }
  public String getName() { return generator.data.title; }
  @Override public boolean equals(Object obj) {
    return getName().contains(((Floor)obj).getName());
  }
  public boolean isExactSame(Floor floor) { return id == floor.id; } 
  protected void spawnStair() {
    AArray<Stair> stairs = background.getObj(Stair.class);
    Stair homeStair = stairs.getIf((e)->e.destFloorLevel == prevFloor);
    if (homeStair == null) {
      Logger.log("Floor", String.format("No stairs available at: %d, floor (%d) prev (%d)", stairs.size(), generator.data.level, prevFloor));
      if (stairs.size() < 2) Logger.error(new Exception());
      homeStair = stairs.getIf((e)->e.destFloorLevel < generator.data.level);
    }
    player.setState(new MoveState((int)homeStair.bounds.x/generator.data.tileSize, (int)homeStair.bounds.y/generator.data.tileSize, false));
  }
  protected void onStage() {}
  protected void onUnstage() {}
  public void update() {
    Rectangle room = generator.roomsBuilder.getRoomPointSrc(player.getWorldX(), player.getWorldY());
    if (room != null && !generator.data.statistic.visitedRooms.contains(room)) {
      generator.data.statistic.visitedRooms.add(room);
    }
  }
}
