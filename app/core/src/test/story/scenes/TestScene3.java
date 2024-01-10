package test.story.scenes;

import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;
import com.leisure.duncraw.story.components.BlockCameraSceneNode;
import com.leisure.duncraw.story.components.DialogueSceneNode;
import com.leisure.duncraw.story.components.MoveCameraSceneNode;

public class TestScene3 extends SceneQueue {
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
