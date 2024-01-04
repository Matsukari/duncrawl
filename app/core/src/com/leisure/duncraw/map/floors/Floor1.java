package com.leisure.duncraw.map.floors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.EntityHashMap;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.lighting.LightEnvironment;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.TerrainSet;
import com.leisure.duncraw.map.TerrainVariants;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.map.WallType;
import com.leisure.duncraw.map.generator.DeadBodiesFurnisher;
import com.leisure.duncraw.map.generator.LightSourcesFurnisher;
import com.leisure.duncraw.map.generator.MiscDecorationFurnisher;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;
import com.leisure.duncraw.map.loader.TmxLoader;

import lib.math.Pointi;

public class Floor1 extends Floor {
  private TerrainSet content;
  public Floor1(TerrainSetGenerator generator) {
    super(generator);
  }
  @Override
  public void stage(Player player, Tileset tileset, EffectManager effectManager) {
    generator.grounds = new TerrainVariants(tileset.terrainTransform(tileset.filter("terrain", "ground")));
    generator.walls = WallType.getAllWallTypes(tileset);

    generator.wallFurnishers.add(new LightSourcesFurnisher(lightEnvironment, effectManager));
    generator.groundFurnishers.add(new DeadBodiesFurnisher());
    MiscDecorationFurnisher miscDecorationFurnisher = new MiscDecorationFurnisher();
    miscDecorationFurnisher.chests.addAll(Graphics.objsSources.floorChests.get(generator.data.level));
    generator.groundFurnishers.add(miscDecorationFurnisher);
    super.stage(player, tileset, effectManager);
    try {
      TerrainSet prefab = TmxLoader.load(this, generator.data.prefabRooms.get("startRoom"));
      content = background;
      background.objs.clear();
      background = prefab;
      // TerrainSetGenerator.combine(background, prefab, generator.roomsBuilder, Pointi.getRandom(generator.roomsBuilder.rect));
    } catch (Exception e) {e.printStackTrace(); System.exit(-1);}
  }
  @Override
  public void initialSpawn(EnemySpawner spawner) {
    super.initialSpawn(spawner);
    player.setState(new MoveState(background.cols/2, background.rows/2, false));
    for (int i = 0; i < generator.data.maxMob; i++) {
      Enemy enemy = (Enemy)spawner.spawn(spawner.sources.ghost);
      Pointi pos = getTileInRandomRoom();
      enemy.setState(new MoveState(pos.x, pos.y, false));
    }
  }
}
