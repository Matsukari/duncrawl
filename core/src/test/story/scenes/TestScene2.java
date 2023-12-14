package test.story.scenes;

import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneQueue;
import com.leisure.duncraw.story.components.DialogueSceneNode;

public class TestScene2 extends SceneQueue {
  @Override
  public boolean play(GameScreen game) { 
    scenes.add(new DialogueSceneNode(Conversation.fromDat("dat/convs/test.conv")));
    return super.play(game);
  }
  
}
