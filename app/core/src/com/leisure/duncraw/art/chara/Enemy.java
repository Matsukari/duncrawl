package com.leisure.duncraw.art.chara;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.map.Floor;
import com.leisure.duncraw.screen.GameScreen.Context;

import behave.execution.ExecutionContext;
import behave.execution.Executor;
import behave.models.Node;

public class Enemy extends Chara { 
  public Node ai;
  public Executor exec;
  public Enemy(CharaData data) {
    super(data);
  }
  public void startAI(Node ai, Floor floor, Player player, Context gameContext) {
    this.ai = ai;
    ExecutionContext context = new ExecutionContext();
    context.setVariable("chara", this);
    context.setVariable("player", player);
    context.setVariable("floor", floor);
    context.setVariable("context", gameContext);
    if (exec != null) exec.stop();
    exec = new Executor();
    exec.initialize(ai, context);
    exec.start(300, 0);
  }
  @Override
  public void onDeath() {
    super.onDeath();
    if (exec != null) exec.stop();
  }
}
