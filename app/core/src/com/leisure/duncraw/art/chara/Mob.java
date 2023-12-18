package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.data.CharaData;

import behave.execution.Executor;

public class Mob extends Chara {
  public Mob(CharaData data, SpriteBatch batch) {
    super(data, batch);
  }
  public void startAI() {
    Executor exec = new Executor();
    // exec.initialize(createTree());
    // exec.start(1000, 1000);
  }
}
