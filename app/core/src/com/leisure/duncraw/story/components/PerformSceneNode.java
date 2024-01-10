
package com.leisure.duncraw.story.components;

import com.leisure.duncraw.story.SceneNode;
import lib.time.TimePeeker;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.helper.Performer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;

// Linear!
public class PerformSceneNode extends SceneNode {
  public Performer function;
  public Chara chara;
  public TimePeeker time = new TimePeeker();
  public PerformSceneNode(Performer function) {
    this.function = function;
  }
  @Override
  public void invoke(GameScreen game) {
    Logger.log("PerformSceneNode", "perform");
    function.perform();
  }  
  @Override
  public boolean update(GameScreen game) {
    return true;
  }
}
