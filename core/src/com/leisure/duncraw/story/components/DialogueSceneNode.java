package com.leisure.duncraw.story.components;

import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.logging.Logger;
import com.leisure.duncraw.screen.GameScreen;
import com.leisure.duncraw.story.SceneNode;


public class DialogueSceneNode extends SceneNode {
  public Conversation conversation;
  public DialogueSceneNode(Conversation conversation) {
    this.conversation = conversation;
  }
  @Override
  public void invoke(GameScreen game) {
    Logger.log("DialogueSceneNode", "Start");
    game.hudManager.dialogueHud.start(conversation);
  }
  @Override
  public boolean update(GameScreen game) {
    return game.hudManager.dialogueHud.conversation.hasEnded();
  }
}
