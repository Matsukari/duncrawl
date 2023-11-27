package com.leisure.duncraw.art.chara;

import lib.animation.LinearAnimation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.leisure.duncraw.art.Art.TexAnim;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.data.GeneralAnimation;

public class DirAnimation {
  public final LinearAnimation<TextureRegion> anims[] = (LinearAnimation<TextureRegion>[]) new LinearAnimation[3]; 
  public static int FRONT=0, SIDE=1, BACK=2;
  public boolean rightHanded = true;
  public DirAnimation(DirAnimData dat) { 
    rightHanded = dat.rightHanded;
    anims[FRONT] = GeneralAnimation.line(dat.front);
    anims[SIDE] = GeneralAnimation.line(dat.side);
    anims[BACK] = GeneralAnimation.line(dat.back);
  }
  public void flipSide(boolean v) {
    for (TextureRegion frames : anims[SIDE].data.getKeyFrames()) frames.flip(v, false);
  }
  public LinearAnimation<TextureRegion> getSideFlip(int vel) {
    if (vel > 0 && !rightHanded) flipSide(true);
    else if (vel < 0) flipSide(false);
    return anims[SIDE];
  }
}
