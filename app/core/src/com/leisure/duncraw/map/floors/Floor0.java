package com.leisure.duncraw.map.floors;

import com.leisure.duncraw.art.chara.EnemySpawner;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.map.objs.BigDoor;
import com.leisure.duncraw.manager.EffectManager;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.Tileset;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;
import com.leisure.duncraw.map.loader.TmxLoader;

public class Floor0 extends Floor {
  public Floor0(TerrainSetGenerator generator) {
    super(generator, false);
  }
  @Override
  public void stage(Player player, Tileset tileset, EffectManager effectManager) {
    super.stage(player, tileset, effectManager);
    try {
      setTerrainSet(TmxLoader.load(this, generator.data.prefabRooms.get("startRoom")));
    } catch (Exception e) {e.printStackTrace(); System.exit(-1);}
    BigDoor door = background.getObj(BigDoor.class);
    door.connectedFloorLevel = generator.data.level + 1;

  }
  @Override
  public void initialSpawn(EnemySpawner spawner) {
    super.initialSpawn(spawner);
    player.setState(new MoveState(background.cols/2, background.rows/2, false));
  }
}
