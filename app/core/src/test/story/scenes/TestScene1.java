package test.story.scenes;

import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;
import com.leisure.duncraw.story.components.BlockCameraSceneNode;
import com.leisure.duncraw.story.components.DialogueSceneNode;
import com.leisure.duncraw.story.components.FloorSceneNodes;
import com.leisure.duncraw.story.components.MoveCameraSceneNode;
import com.leisure.duncraw.story.components.MoveCharaSceneNode;

public class TestScene1 extends SceneQueue {
  @Override
  public boolean play(GameScreen game) {
    Logger.toogleGroup();
    scenes.add(new FloorSceneNodes.ChangeFloor(0));
    scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_1.conv")));
    scenes.add(new MoveCameraSceneNode(new Vector2(game.player.bounds.x, game.player.bounds.y + 209), 3f));
    return super.play(game);
  }
}
