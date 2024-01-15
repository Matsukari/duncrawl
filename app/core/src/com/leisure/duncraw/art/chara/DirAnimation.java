package com.leisure.duncraw.art.chara;

import lib.animation.LinearAnimation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.leisure.duncraw.data.DirAnimData;
import com.leisure.duncraw.data.GeneralAnimation;

public class DirAnimation {
  @SuppressWarnings("unchecked")
  public final LinearAnimation<TextureRegion> anims[] = (LinearAnimation<TextureRegion>[]) new LinearAnimation[3]; 
  public LinearAnimation<TextureRegion> currentDir;
  public static int FRONT=0, SIDE=1, BACK=2;
  public boolean rightHanded = true;
  public DirAnimation(DirAnimData dat, int size) { 
    rightHanded = dat.rightHanded;
    anims[FRONT] = GeneralAnimation.line(dat.front, PlayMode.LOOP, size);
    anims[SIDE] = GeneralAnimation.line(dat.side, PlayMode.LOOP, size);
    anims[BACK] = GeneralAnimation.line(dat.back, PlayMode.LOOP, size);
    currentDir = anims[DirAnimation.FRONT];
  }
  public DirAnimation(DirAnimData dat) { 
    rightHanded = dat.rightHanded;
    anims[FRONT] = GeneralAnimation.line(dat.front);
    anims[SIDE] = GeneralAnimation.line(dat.side);
    anims[BACK] = GeneralAnimation.line(dat.back);
    currentDir = anims[DirAnimation.FRONT];
  }
  public void flipSide(boolean v) {
    for (TextureRegion frames : anims[SIDE].data.getKeyFrames()) frames.flip(v, false);
  }
  public void setAnimDur(float dur) {
    for (LinearAnimation<TextureRegion> anim : anims) anim.data.setFrameDuration(dur);
  }
  public void setPlayMode(PlayMode mode) {
    for (LinearAnimation<TextureRegion> anim : anims) anim.data.setPlayMode(mode);
  }
  public void replay() {
    currentDir.reset();
  }
  public LinearAnimation<TextureRegion> getSideFlip(int vel) {
    if (vel > 0 && !anims[SIDE].data.getKeyFrames()[0].isFlipX()) flipSide(!rightHanded);
    else if (vel < 0 && anims[SIDE].data.getKeyFrames()[0].isFlipX()) flipSide(!rightHanded);
    else if (vel < 0) flipSide(rightHanded);
    return anims[SIDE];
  }
  public void face(int x, int y) {
    if (x != 0) currentDir = getSideFlip(x).clone();
    else if (y < 0) currentDir = anims[DirAnimation.FRONT].clone();
    else if (y > 0) currentDir = anims[DirAnimation.BACK].clone();
  }
}
