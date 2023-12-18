package test.story.scenes;

import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;
import com.leisure.duncraw.story.components.BlockCameraSceneNode;
import com.leisure.duncraw.story.components.MoveCameraSceneNode;
import com.leisure.duncraw.story.components.MoveCharaSceneNode;

public class TestScene1 extends SceneQueue {
  @Override
  public boolean play(GameScreen game) {
    scenes.add(new MoveCharaSceneNode(game.player, 10, 0));
    scenes.add(new BlockCameraSceneNode(true));
    scenes.add(new MoveCameraSceneNode(new Vector2(0, 0), 3f));
    scenes.add(new BlockCameraSceneNode(false));
    return super.play(game);
  }
}
