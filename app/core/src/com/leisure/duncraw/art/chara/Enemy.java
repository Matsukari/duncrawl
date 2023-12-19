package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.leisure.duncraw.data.CharaData;

import behave.execution.ExecutionContext;
import behave.execution.Executor;
import behave.models.Node;

public class Enemy extends Chara {  
  public Enemy(CharaData data, SpriteBatch batch) {
    super(data, batch);
  }
  public void startAI(Node ai, Player player) {
    ExecutionContext context = new ExecutionContext();
    context.setVariable("chara", this);
    context.setVariable("player", player);
    Executor exec = new Executor();
    exec.initialize(ai, context);
    exec.start(300, 200);
  }
}
