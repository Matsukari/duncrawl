package com.leisure.duncraw.story.components;

import com.leisure.duncraw.story.SceneNode;
import com.badlogic.gdx.math.Vector2;
import lib.time.TimePeeker;
import com.badlogic.gdx.math.Vector3;
import com.leisure.duncraw.screen.GameScreen;

public class MoveCameraSceneNode extends SceneNode {
  public Vector3 dst = Vector3.Zero;
  public TimePeeker timer = new TimePeeker();
  public float dur;
  public MoveCameraSceneNode(Vector2 dst, float dur) {
    this.dst.x = dst.x;
    this.dst.y = dst.y;
    this.dur = dur;

  }
  @Override
  public void invoke(GameScreen game) {
    game.blockCamera = true;
    timer.peek();
  }  
  @Override
  public boolean update(GameScreen game) {
    float time = timer.sinceLastPeek()/1000f/dur/dur/dur; 
    game.camera.position.lerp(dst, time);
    if (timer.sinceLastPeek() >= 1000f * dur) return true;
    return false;
  }
}
