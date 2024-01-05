package com.leisure.duncraw.map.floors;

import java.util.ArrayList;

import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.map.TilemapChara;
import com.leisure.duncraw.art.map.objs.Stair;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.map.WallType;
import com.leisure.duncraw.map.generator.GhostLairFurnisher;
import com.leisure.duncraw.map.generator.LightSourcesFurnisher;
import com.leisure.duncraw.map.generator.EssensialsFurnisher;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;

import lib.math.Pointi;

public class Floor1 extends Floor {
  public Floor1(TerrainSetGenerator generator) {
    super(generator);
  }
  @Override
  protected void onStage() {
    generator.grounds = tileset.getTerrainVariants("ground");
    generator.walls = WallType.getAllWallTypes(tileset);

    generator.wallFurnishers.add(new LightSourcesFurnisher(lightEnvironment, effectManager));
    generator.groundFurnishers.add(new GhostLairFurnisher());
    EssensialsFurnisher essenFurnisher = new EssensialsFurnisher(this, tileset);
    essenFurnisher.chests.addAll(generator.data.chests);
    generator.groundFurnishers.add(essenFurnisher);
  } 
  @Override
  protected void onUnstage() {
    Logger.log("Floor1", "Unstaging");
    Logger.log("Floor1", "Total objects : " + Integer.toString(background.objs.data.size()));
    // for (TilemapChara chara : chars) { 
    //   chara.chara.kill();
    // }
  }
  @Override
  public void initialSpawn(EnemySpawner spawner) {
    super.initialSpawn(spawner);
    ArrayList<Stair> stairs = background.getObj(Stair.class);
    Stair homeStair = null;
    if (stairs.get(0).destFloorLevel < generator.data.level) homeStair = stairs.get(0);
    else if (stairs.get(1).destFloorLevel < generator.data.level) homeStair = stairs.get(1); 
    else { Logger.error("Floor1", "No stairs available"); }
    player.setState(new MoveState((int)homeStair.bounds.x/generator.data.tileSize, (int)homeStair.bounds.y/generator.data.tileSize, false));
    for (int i = 0; i < generator.data.maxMob; i++) {
      Enemy enemy = (Enemy)spawner.spawn(spawner.sources.ghost);
      Pointi pos = getTileInRandomRoom();
      enemy.setState(new MoveState(pos.x, pos.y, false));
    }
  }
}
