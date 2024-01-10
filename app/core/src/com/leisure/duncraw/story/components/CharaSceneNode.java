
package com.leisure.duncraw.story.components;

import com.leisure.duncraw.story.SceneNode;
import lib.time.TimePeeker;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.screen.GameScreen;

// Linear!
public class CharaSceneNode extends SceneNode {
  @FunctionalInterface
  public static interface CharaFunction {
    public void perform(Chara chara);

  }
  public CharaFunction function;
  public Chara chara;
  public TimePeeker time = new TimePeeker();
  public CharaSceneNode(Chara chara, CharaFunction function) {
    this.chara = chara;
    this.function = function;
  }
  @Override
  public void invoke(GameScreen game) {
    function.perform(chara);
  }  
  @Override
  public boolean update(GameScreen game) {
    return true;
  }
}
