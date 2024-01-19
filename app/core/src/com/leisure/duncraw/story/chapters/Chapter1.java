package com.leisure.duncraw.story.chapters;

import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.art.chara.Enemy;
import com.leisure.duncraw.art.chara.Npc;
import com.leisure.duncraw.art.chara.ai.enemy.GhostKingAi;
import com.leisure.duncraw.art.chara.states.HurtState;
import com.leisure.duncraw.art.chara.states.MoveState;
import com.leisure.duncraw.data.CharaData;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;
import com.leisure.duncraw.story.components.BlockCameraSceneNode;
import com.leisure.duncraw.story.components.CharaSceneNode;
import com.leisure.duncraw.story.components.ConditionalSceneNode;
import com.leisure.duncraw.story.components.DialogueSceneNode;
import com.leisure.duncraw.story.components.FloorSceneNodes;
import com.leisure.duncraw.story.components.MoveCameraSceneNode;
import com.leisure.duncraw.story.components.MoveCharaSceneNode;
import com.leisure.duncraw.story.components.ParallelSceneNode;
import com.leisure.duncraw.story.components.PerformSceneNode;
import com.leisure.duncraw.story.components.SequenceSceneNode;
import com.leisure.duncraw.story.components.CharaSceneNode.CharaFunction;

import lib.time.Timer;

public class Chapter1 {
  public static class Scene1 extends SceneQueue {
    @Override
    public boolean play(GameScreen game) {
      Logger.toogleGroup();
      scenes.add(new FloorSceneNodes.ChangeFloor(0));
      scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_1.conv")));
      scenes.add(new MoveCameraSceneNode(new Vector2(game.player.bounds.x, game.player.bounds.y + 209), 3f));
      return start(game);
    }
  }
  public static class Scene2 extends SceneQueue {
    @Override
    public boolean play(GameScreen game) { 
      scenes.add(new MoveCameraSceneNode(new Vector2(game.player.bounds.x, game.player.bounds.y), 3f)); 
      scenes.add(new BlockCameraSceneNode(false));
      scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_2.conv")));
      // scenes.add(new DialogueSceneNode(Conversation.fromDat("dat/convs/test.conv")));
      return start(game);
    }

  }
  public static class Scene3 extends SceneQueue {
    @Override
    public boolean update(GameScreen game) {
      if (!started) {
        if (game.floorManager.getFloor().generator.data.level == 1) {
          scenes.add(new MoveCameraSceneNode(new Vector2(game.player.bounds.x, game.player.bounds.y + 100), 3f)); 
          scenes.add(new BlockCameraSceneNode(false));
          scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_3.conv")));
          start(game);
        }
        return false;
      }
      return super.update(game);
    }

  }
  public static class Scene4 extends SceneQueue {
    @Override
    public boolean update(GameScreen game) {
      if (!started) {
        if (game.floorManager.getFloor().generator.data.level == 1 &&
            game.floorManager.getFloor().generator.data.statistic.visitedRooms.size() > 2) {
          // scenes.add(new BlockCameraSceneNode(false));
          Enemy boss = 
            game.floorManager.getFloor().spawner.spawn(new Enemy(game.floorManager.getFloor().spawner.sources.ghostKing)); 
          scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_4.conv")));
          scenes.add(new MoveCharaSceneNode(boss, new MoveState(game.player.mapAgent.x, game.player.mapAgent.y+3, false)));
          scenes.add(new ParallelSceneNode(
                new SequenceSceneNode(
                  new MoveCharaSceneNode(boss, new MoveState(0, -7, true)),
                  new PerformSceneNode(()->{game.player.setState(new HurtState(boss, true)); boss.kill();})
                  ),
                (new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_5.conv")))
                ));
          start(game);
        }
        return false;
      }
      return super.update(game);
    }

  }
  public static class Scene5 extends SceneQueue {
    @Override
    public boolean update(GameScreen game) {
      if (!started) {
        if (game.floorManager.getFloor().generator.data.level == 2) {
          scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_6.conv")));
          scenes.add(new MoveCameraSceneNode(new Vector2(game.player.bounds.x, game.player.bounds.y + 200), 5f)); 
          scenes.add(new MoveCharaSceneNode(
                game.charaManager.getByType(Npc.class, game.charaManager.sources.elder), new MoveState(0, -10, true)));
          scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_7.conv")));
          start(game);
        }
        return false;
      }
      return super.update(game);
    }

  }
  public static class Scene6 extends SceneQueue {
    @Override
    public boolean update(GameScreen game) {
      if (!started) {
        if (game.floorManager.getFloor().generator.data.level == 2) {
          scenes.add(new MoveCameraSceneNode(new Vector2(game.player.bounds.x, game.player.bounds.y), 5f));
          scenes.add(new BlockCameraSceneNode(false));
          scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_8.conv")));
          start(game);
        }
        return false;
      }
      return super.update(game);
    }

  }
  public static class Scene7 extends SceneQueue {
    private Timer delay = new Timer(2000); 
    private Timer delayFinish= new Timer(1000);
    private Enemy boss;
    @Override
    public boolean update(GameScreen game) {
      if (!started) {
        if (game.floorManager.getFloor().generator.data.level == 4 && !delay.isTicking() && boss == null) {
          delay.start(); 
          Enemy boss = game.floorManager.getFloor().spawner.spawn(new Enemy(game.floorManager.getFloor().spawner.sources.ghostKing)); 
          boss.setState(new MoveState(game.floorManager.getFloor().background.cols/2, game.floorManager.getFloor().background.rows/2, false));
        
          scenes.add(new ParallelSceneNode(
            new MoveCharaSceneNode(game.player, new MoveState(0, game.player.mapAgent.y+3, true)),
            new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_9.conv"))
          ));
          scenes.add(new PerformSceneNode(()->{
            boss.startAI(new GhostKingAi(), game.floorManager.getFloor(), game.player, game.context);
          }));
          scenes.add(new ConditionalSceneNode(()->{
            return boss.status.dead;
          }));
          scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_10.conv")));
          scenes.add(new PerformSceneNode(()->{
            game.changeScreen = true;
          }));
          start(game);
        }
        return false;
      }
      return super.update(game);
    }

  }
}
