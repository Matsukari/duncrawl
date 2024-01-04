package com.leisure.duncraw.art.map.objs;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.art.item.Item;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ItemData;
import com.leisure.duncraw.data.ObjData;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.map.Floor;

import lib.animation.LinearAnimation;
import lib.time.Timer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class BigDoor extends Obj {
  public boolean opened = false;
  private Timer timer = new Timer(100);
  private LinearAnimation<TextureRegion> open;
  private final Floor floor;
  private int connectedFloorLevel;
  public static class BigDoorData extends ItemData {
    public int connectedFloorLevel;
    @Override
    public void reset() {
      connectedFloorLevel = 0;
      super.reset();
    }
  }
  public BigDoor(String datFile, Floor floor) {
    super(datFile);
    this.floor = floor;
  }
  @Override
  public void load(String datFile) {
    BigDoorData chestData = loadType(datFile, BigDoorData.class);
    connectedFloorLevel = chestData.connectedFloorLevel;
    open = GeneralAnimation.line(dat.anims.get("open"), PlayMode.NORMAL, Math.max(dat.size.x, dat.size.y) * 16);
  }
  @Override
  public void onCharaInteract(Chara chara) {
    anim = open;
    anim.reset();
    timer.start();
  }
  @Override
  public void render(SpriteBatch batch) {
    if (open.isFinished() && timer.isFinished()) {
      opened = true;
      floor.nextLevel = connectedFloorLevel;
    }
    super.render(batch);
  }
}
