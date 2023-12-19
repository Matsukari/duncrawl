package com.leisure.duncraw.map.floors;

import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.data.FloorData;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

import lib.math.Pointi;

public class Floor1 extends Floor {
  private FloorData data;
  public Floor1(TerrainSetGenerator generator) {
    super(generator);
    data = generator.data;
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
