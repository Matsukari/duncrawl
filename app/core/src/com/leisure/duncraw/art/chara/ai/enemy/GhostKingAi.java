package com.leisure.duncraw.art.chara.ai.enemy;

import java.security.ProtectionDomain;
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
import com.leisure.duncraw.art.chara.observers.AnimationBehaviour;
import com.leisure.duncraw.art.chara.states.AttackState;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.art.gfx.GfxAnimation;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen.Context;

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
    Chara chara = ((Chara)context.getVariable("chara"));
    chara.observers.add(new AnimationBehaviour(((Context)context.getVariable("context")).effectManager));
    chara.dat.knockable = false;
    
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
        chara.setState(new MoveState(randomPos.x, randomPos.y, false), true);

      }
      if (chara.status.health <= chara.status.maxHealth * 0.35f) {
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
          Enemy enemy = new Enemy(getFloor(context).spawner.sources.ghost);
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
    private Timer timer = new Timer(4000);
    private Timer projTimer = new Timer(4000);
    private int count = 0;
    private GfxAnimation projectile;
    private double angle;
    private Rectangle intersection = new Rectangle();
    public HurlBall() { timer.start(); }
    @Override
    public void initialize(ExecutionContext context) {
      super.initialize(context);
    }
    @Override
    public Status tick(ExecutionContext context) {
      // Logger.log("GhostKingAi", "Hurling " + Float.toString(timer.normalize()));
      hurl(context);
      update(context);
      return Status.Success;
    }
    protected void hurl(ExecutionContext context) {
      if (timer.isFinished()) {
        stop(context);
        timer.reset();
        count ++;
        context.setVariable("hurled_ball", count);
        chara.anims.set("hurl");
        chara.anims.get("hurl").currentDir.stateTime = 0.1f;
        chara.anims.get("hurl").setPlayMode(PlayMode.NORMAL);
        projectile = new GfxAnimation(chara.anims.get("ghost_ball").currentDir, true);
        getContext(context).effectManager.start(projectile);
        Vector2 p1 = new Vector2(), p2 = new Vector2();
        chara.bounds.getCenter(p1);
        player.bounds.getCenter(p2);
        angle = Math.atan2(p2.y-p1.y, p1.x - p2.x);
        projectile.bounds.setSize(32);
        projectile.bounds.setPosition(p1.x, p1.y);
        projTimer.start();
        // Logger.log("GhostKingAi", "Angle: " + Double.toString(angle));
      }

    }
    protected void update(ExecutionContext context) {
      if (projectile != null) {
        projectile.bounds.x -= Math.cos(angle) * 200f * Gdx.graphics.getDeltaTime();
        projectile.bounds.y += Math.sin(angle) * 200f * Gdx.graphics.getDeltaTime();
        if (Intersector.intersectRectangles(projectile.bounds, player.bounds, intersection)) {
          onHit(context);
          stop(context);
          chara.movement.lastVelX = -1;
          player.setState(new HurtState(chara, true), true);
        }
        if (projTimer.isFinished() || chara.status.dead) {
          stop(context);
        }
      }

    }
    public void stop(ExecutionContext context) {
      getContext(context).effectManager.stop(projectile);
      projectile = null;
      projTimer.stop();

    }
    protected void onHit(ExecutionContext context) {}
  }
  public static class HurlBallExplodeIf extends HurlBall {
    private int countThreshold = 3;
    @Override
    public Status tick(ExecutionContext context) {
      if ((int)context.getVariable("hurled_ball") >= countThreshold) {
        context.setVariable("hurled_ball", 0);
        hurl(context);
      }
      update(context);
      return Status.Success;
    }
    @Override
    protected void onHit(ExecutionContext context) {
      GfxAnimation animation = new GfxAnimation(chara.anims.get("ghost_ball_explode").currentDir, false);
      getContext(context).effectManager.start(animation);
      animation.centerTo(player);
      animation.bounds.setSize(64, 64);
      Logger.log("GhostKingAi", "Ghit");
    }
  }


}
