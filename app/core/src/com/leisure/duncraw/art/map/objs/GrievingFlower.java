package com.leisure.duncraw.art.map.objs;

import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.map.Obj;
import com.leisure.duncraw.data.GeneralAnimation;
import com.leisure.duncraw.helper.SString;
import com.leisure.duncraw.logging.Logger;

import lib.animation.LinearAnimation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class GrievingFlower extends Obj {
  private LinearAnimation<TextureRegion> open;
  public GrievingFlower(String datFile) {
    super(datFile);
    open = GeneralAnimation.line(dat.anims.get("open"), PlayMode.NORMAL);
  }
  @Override
  public void onCharaInteract(Chara chara) {
    anim = open;
    anim.reset();
  }
}

