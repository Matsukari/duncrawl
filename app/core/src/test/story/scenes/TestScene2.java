package test.story.scenes;

import com.badlogic.gdx.math.Vector2;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.data.Deserializer;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;
import com.leisure.duncraw.story.components.BlockCameraSceneNode;
import com.leisure.duncraw.story.components.DialogueSceneNode;
import com.leisure.duncraw.story.components.MoveCameraSceneNode;

public class TestScene2 extends SceneQueue {
  @Override
  public boolean play(GameScreen game) { 
    scenes.add(new MoveCameraSceneNode(new Vector2(game.player.bounds.x, game.player.bounds.y), 3f)); 
    scenes.add(new BlockCameraSceneNode(false));
    scenes.add(new DialogueSceneNode(Deserializer.safeLoad(Conversation.class, "dat/story/ch1_2.conv")));
    // scenes.add(new DialogueSceneNode(Conversation.fromDat("dat/convs/test.conv")));
    return super.play(game);
  }
  
}
