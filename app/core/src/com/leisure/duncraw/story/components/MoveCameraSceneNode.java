package com.leisure.duncraw.story.components;

import com.leisure.duncraw.story.SceneNode;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import lib.time.TimePeeker;
import lib.time.Timer;

import com.badlogic.gdx.math.Vector3;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;

public class MoveCameraSceneNode extends SceneNode {
  public Vector3 dst = Vector3.Zero;
  public Timer timer;
  public float dur;
  public MoveCameraSceneNode(Vector2 dst, float dur) {
    this.dst.x = dst.x;
    this.dst.y = dst.y;
    this.dur = dur;
    timer = new Timer(dur * 1000);

  }
  @Override
  public void invoke(GameScreen game) {
    timer.start();
  }  
  @Override
  public boolean update(GameScreen game) {
    game.blockCamera = true;
    game.camera.position.lerp(dst, timer.normalize());
    // Logger.log("MoveCameraSceneNode", Float.toString(game.camera.position.y));
    if (timer.isFinished()) return true;
    return false;
  }
}
