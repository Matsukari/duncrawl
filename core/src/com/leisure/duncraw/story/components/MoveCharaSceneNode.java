package com.leisure.duncraw.story.components;

import com.leisure.duncraw.story.SceneNode;
import lib.time.TimePeeker;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.screen.GameScreen;

// Linear!
public class MoveCharaSceneNode extends SceneNode {
  public Chara chara;
  public int startX, startY;
  public int dstX, dstY;
  public boolean started = false;
  public TimePeeker time = new TimePeeker();
  public MoveCharaSceneNode(Chara chara, int dstX, int dstY) {
    this.chara = chara;
    this.dstX = dstX;
    this.dstY = dstY;
    startX = dstX;
    startY = dstY;
  }
  @Override
  public void invoke(GameScreen game) {
    chara.moveBy(dstX, dstY);
    started = true;
  }  
  @Override
  public boolean update(GameScreen game) {
    return (started && !chara.movement.isMoving());
  }
}
