package com.leisure.duncraw.map.floors;

import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.Spawner;
import com.leisure.duncraw.art.chara.ai.AiWanderer;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;
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

    generator.wallFurnishers.add(new LightSourcesFurnisher(lightEnvironment, context.effectManager));
    generator.groundFurnishers.add(new GhostLairFurnisher());
    EssensialsFurnisher essenFurnisher = new EssensialsFurnisher(this, tileset, context);
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
  public void initialSpawn(Spawner spawner) {
    super.initialSpawn(spawner);
    for (int i = 0; i < generator.data.maxMob; i++) {
      Enemy enemy = new Enemy(spawner.sources.ghost);
      spawner.spawn(enemy);
      Pointi pos = getTileInRandomRoom();
      enemy.setState(new MoveState(pos.x, pos.y, false));
      enemy.startAI(new AiWanderer(),this, player, context);
    }
    try { spawnStair(); }
    catch (Exception exception) {

    } 
  }
}
