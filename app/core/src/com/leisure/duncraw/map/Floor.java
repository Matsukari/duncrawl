package com.leisure.duncraw.map;

import java.util.ArrayList;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.art.map.Decoration;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.art.map.Terrain;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.helper.IdGenerator;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

import lib.math.Pointi;

public class Floor {
  public final ArrayList<TilemapChara> chars = new ArrayList<>();
  public final TerrainSetGenerator generator;
  public TerrainSet background;
  public TerrainSet foreground;
  public LightEnvironment lightEnvironment;
  public EffectManager effectManager;
  public Tileset tileset;
  public EnemySpawner spawner;
  public int nextLevel = -1;
  public Player player;
  public int id;
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
  public void stage(Player player, Tileset tileset, EffectManager effectManager) {
    this.player = player;
    this.effectManager = effectManager;
    this.tileset = tileset;
    onStage();
    generator.populate(background);
    for (Map.Entry<Pointi, Obj> obj : background.objs.data.entrySet()) {
      obj.getValue().onStage(this);
    }
  }
  protected void onStage() {}
  protected void onUnstage() {}
  public void unstage() {
    for (Map.Entry<Pointi, Obj> obj : background.objs.data.entrySet()) {
      obj.getValue().onUnstage(this);
    }
    for (TilemapChara agent : chars) {
      if (!(agent.chara instanceof Player)) agent.chara.kill();
    }
    background.objs.clear();
    nextLevel = -1;
    onUnstage();
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
      if (chara.onBlock(x, y) && !chara.chara.status.dead) return chara;
    }
    return null;
  }
  public void update() {}
  protected void setTerrainSet(TerrainSet terrainSet) {
    background = terrainSet;
    lightEnvironment = new LightEnvironment(generator.data.envColor, new Rectangle(0, 0, background.getWidth(), background.getHeight()));
  }
  public void initialSpawn(EnemySpawner spawner) { this.spawner = spawner; } 
  public boolean canTravel(int x, int y) {
    Terrain terrain = background.getTerrain(x, y);
    Obj obj = background.getObj(x, y);
    TilemapChara chara = getChara(x, y);
    boolean traversable = true;
    if (terrain != null && !terrain.traversable()) traversable = false;
    if (obj != null && !(obj instanceof Item) && !(obj instanceof Decoration) && !(obj instanceof Terrain)) traversable = false;
    if (chara != null) traversable = false;
    return traversable;
  }
  public Pointi getTileInRandomRoom() {
    Pointi tile = new Pointi(0, 0);
    if (generator == null) return tile;
    Rectangle room = generator.roomsBuilder.rooms.get(MathUtils.random(generator.roomsBuilder.mainRooms.size()-1));
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
}
