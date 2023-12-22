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

public class Chest extends Obj {
  private LinearAnimation<TextureRegion> open;
  public Chest(String datFile) {
    super(datFile);
    open = GeneralAnimation.line(dat.anims.get("open"), PlayMode.NORMAL);
  }
  @Override
  public void update(float dt) {
  }
  @Override
  public void onCharaInteract(Chara chara) {
    anim = open;
    anim.reset();
  }
  @Override
  public void render(SpriteBatch batch) {
    super.render(batch);
    // Logger.log("Obj", String.format("Rendering at %s, frame: %d, total=%d", SString.toString(bounds), anim.data.getKeyFrameIndex(anim.stateTime), anim.data.getKeyFrames().length));
  }
}
