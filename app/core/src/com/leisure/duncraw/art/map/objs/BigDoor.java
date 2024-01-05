package com.leisure.duncraw.art.map.objs;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.data.ItemData;
import com.leisure.duncraw.data.ObjData;
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
  public int connectedFloorLevel;
  public BigDoor(String datFile, Floor floor) {
    super(datFile);
    this.floor = floor;
  }
  @Override
  public void load(String datFile) {
    loadType(datFile, ObjData.class);
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
