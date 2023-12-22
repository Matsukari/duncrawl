package com.leisure.duncraw.map.floors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.TerrainSet;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;
import com.leisure.duncraw.map.loader.TmxLoader;

import lib.math.Pointi;

public class Floor1 extends Floor {
  private FloorData data;
  public Floor1(TerrainSetGenerator generator, SpriteBatch batch) {
    super(generator);
    data = generator.data; 
    // TerrainSet prefab = TmxLoader.load(data.prefabRooms.get("startRoom"), batch, data.tileSize, data.tileSize)[0];
    // TerrainSetGenerator.combine(terrainSet, prefab, meta, meta.mainRooms.get(MathUtils.random(meta.mainRooms.size()-1)));
  }
  @Override
  public void initialSpawn(EnemySpawner spawner) {
    super.initialSpawn(spawner);
    for (int i = 0; i < data.maxMob; i++) {
      Enemy enemy = (Enemy)spawner.spawn(spawner.sources.ghost);
      Pointi pos = getTileInRandomRoom();
      enemy.setState(new MoveState(pos.x, pos.y, false));
    }
  }
}
