package com.leisure.duncraw.map.floors;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Npc;
import com.leisure.duncraw.art.chara.Spawner;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.helper.Pair;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.map.generator.TerrainSetGenerator;
import com.leisure.duncraw.map.loader.TmxLoader;

import lib.math.Pointi;

public class Floor2 extends Floor {
  TmxLoader loader = new TmxLoader();
  public Floor2(TerrainSetGenerator generator) {
    super(generator, false);
  }
  @Override
  protected void onStage() {
    try {
      setTerrainSet(loader.load(this, context, generator.data.prefabRooms.get("safezone")));
    } catch (Exception e) {e.printStackTrace(); System.exit(-1);}
  }
  @Override
  public void initialSpawn(Spawner spawner) {
    super.initialSpawn(spawner);
    for (Pair<Pointi, Chara> chara : loader.charas) {
      spawner.spawn(chara.b);
      chara.b.setState(new MoveState(chara.a.x, chara.a.y, false));
    }
    spawnStair();
  }
}
