package com.leisure.duncraw.story.components;

import com.leisure.duncraw.story.SceneNode;
import lib.time.TimePeeker;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.screen.GameScreen;

// Linear!
public class MoveCharaSceneNode extends SceneNode {
  public Chara chara;
  public MoveState state;
  public boolean started = false;
  public TimePeeker time = new TimePeeker();
  public MoveCharaSceneNode(Chara chara, MoveState state) {
    this.chara = chara;
    this.state = state;
  }
  @Override
  public void invoke(GameScreen game) {
    chara.setState(state);
    started = true;
  }  
  @Override
  public boolean update(GameScreen game) {
    return (started && !chara.movement.isMoving());
  }
}
