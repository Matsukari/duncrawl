package com.leisure.duncraw.art.chara.observers;

import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.TalkState;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.hud.DialogueHud;

public class TalkBehaviour extends Observer {
  private final DialogueHud dialogueHud;
  private final Conversation conversation;
  public TalkBehaviour(DialogueHud dialogueHud, Conversation conversation) {
    this.dialogueHud = dialogueHud;
    this.conversation = conversation;
  }
  @Override
  public void invoke(State state) {
    if (state instanceof TalkState) {
      if (chara.prevState instanceof TalkState) {
        if (!dialogueHud.next()) chara.setState(new IdleState());
      }
      else dialogueHud.start(conversation);
    } 
  } 
  @Override
  public Observer copy() {
    return new TalkBehaviour(dialogueHud, conversation);
  }
}
