package com.leisure.duncraw.art.chara.ai.enemy;

import java.util.ArrayList;

import org.jgrapht.util.MathUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.chara.Chara;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.ai.AiWanderer;
import com.leisure.duncraw.art.chara.ai.components.CharaLeafNode;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.logging.Logger;

import behave.execution.ExecutionContext;
import behave.models.DecoratorNode;
import behave.models.CompositeNode.SequenceNode;
import behave.models.Types.Status;
import lib.math.Pointi;
import lib.time.Timer;

public class GhostKingAi extends DecoratorNode.InfiniteRepeaterNode {
  public GhostKingAi() {
  }
  @Override
  public void initialize(ExecutionContext context) {
    SequenceNode peakState = new SequenceNode();
    SequenceNode weakenedState = new SequenceNode();

    peakState.addChild(new HurlBall());
    peakState.addChild(new GoAwayIfDamaged());
    weakenedState.addChild(new SummonWaveIf());
    weakenedState.addChild(new HurlBall());
    weakenedState.addChild(new HurlBallExplodeIf());
    SequenceNode sequencer = new SequenceNode();
    sequencer.addChild(peakState);
    sequencer.addChild(weakenedState);
    addChild(sequencer);
    sequencer.initialize(context);
  }
  public static class GoAwayIfDamaged extends CharaLeafNode {
    private int lastHealth;
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context);
      lastHealth = chara.status.health;
    }
    @Override
    public Status tick(ExecutionContext context) {
      if (chara.status.health != lastHealth) {
        lastHealth = chara.status.health;
        // getContext(context).effectManager.start(new InterpolationEffect(chara, Interpolation.fade, 2f));
        Pointi randomPos = getFloor(context).getTileInRandomRoom();
        // Pointi randomPos = new Pointi(MathUtils.random(chara.mapAgent.x-5, chara.mapAgent.x+5), MathUtils.random(chara.mapAgent.y-5, chara.mapAgent.y+5));
        chara.setState(new MoveState(randomPos.x, randomPos.y, false));

      }
      if (chara.status.health <= 30) {
        Logger.log("GhostKingAi", "Reached weakend");
        return Status.Success;
      }
      return Status.Failure;
    }
  }
  public static class TurnSolid extends CharaLeafNode {
  }
  public static class SummonWaveIf extends CharaLeafNode {
    private ArrayList<Chara> remains = new ArrayList<>();
    private int maxSpawn = 5;
    @Override
    public Status tick(ExecutionContext context) {
      if (remains.size() < 5 && MathUtils.randomBoolean(0.5f)) {
        int spawns = MathUtils.random(maxSpawn-5, maxSpawn+5);
        
        for (int i = 0; i < spawns; i++) {
          Enemy enemy = new Enemy(Deserializer.safeLoad(CharaData.class, getFloor(context).spawner.sources.ghost));
          remains.add(enemy);
          getFloor(context).spawner.spawn(enemy);
          enemy.setState(new MoveState(getFloor(context).getTileInRandomRoom()));
          enemy.startAI(new AiWanderer(), getFloor(context), player, getContext(context));
        }
      }
      for (int i = 0; i < remains.size(); i++) { 
        if (remains.get(i).status.dead) remains.remove(i);
      }
      return Status.Success;
    }
  }
  
  public static class HurlBall extends CharaLeafNode {
    private Timer timer = new Timer(1000);
    private Timer projTimer = new Timer(3000);
    private int count = 0;
    private GfxAnimation projectile;
    private double angle;
    private Vector2 lastPlayer;
    private Rectangle intersection = new Rectangle();
    public HurlBall() { timer.start(); }
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context);
      timer.start();
    }
    @Override
    public Status tick(ExecutionContext context) {
      if (timer.isFinished()) {
        timer.stop();
        count ++;
        context.setVariable("hurled_ball", count);
        chara.anims.set("hurl");
        chara.anims.get("hurl").setPlayMode(PlayMode.NORMAL);
        projectile = new GfxAnimation(chara.anims.get("ghost_ball").currentDir, true);
        getContext(context).effectManager.start(projectile);
        Vector2 p1 = new Vector2(), p2 = new Vector2();
        chara.bounds.getCenter(p1);
        player.bounds.getCenter(p2);
        angle = Math.atan2(p2.y-p1.y, p1.x - p2.x);
        Logger.log("GhostKingAi", "Angle: " + Double.toString(angle));
        // position = new Vector2(chara.bounds.x, chara.bounds.y);
        // position.lerp(lastPlayer, projTimer.normalize()*0.02f);
        lastPlayer = new Vector2(player.bounds.x, player.bounds.y);
        projectile.bounds.setSize(32);
        projectile.bounds.setPosition(p1.x, p1.y);
        projTimer.start();
        // chara.setState(new AttackState(player));
      }
      if (projectile != null) {
        if (chara.anims.get("hurl").currentDir.isFinished()) { chara.anims.set("idle"); }
        // projectile.bounds.setPosition(position);
        projectile.bounds.x -= Math.cos(angle) * 100f * Gdx.graphics.getDeltaTime();
        projectile.bounds.y += Math.sin(angle) * 100f * Gdx.graphics.getDeltaTime();
        if (Intersector.intersectRectangles(projectile.bounds, player.bounds, intersection)) {
          stop(context);
          // chara.movement.lastVelX = (int)Math.cos(angle);
          // chara.movement.lastVelY = (int)Math.sin(angle);
          chara.movement.lastVelX = -1;
          player.setState(new HurtState(chara, true), true);
          return Status.Success;
        }
        if (projTimer.isFinished()) {
          stop(context);
          return Status.Success;
        }
      }
      return Status.Running;
    }
    public void stop(ExecutionContext context) {
      getContext(context).effectManager.stop(projectile);
      projectile = null;
      projTimer.stop();

    }
  }
  public static class HurlBallExplodeIf extends CharaLeafNode {
    private int countThreshold = 3;
    @Override
    public Status tick(ExecutionContext context) {
      if ((int)context.getVariable("hurled_ball") >= countThreshold) {
        context.setVariable("hurled_ball", 0);
        chara.anims.set("hurl");
        // chara.setState(new AttackState(player));
      }
      return Status.Failure;
    }
  }


}
