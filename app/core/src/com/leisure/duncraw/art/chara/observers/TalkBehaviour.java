package com.leisure.duncraw.art.chara.observers;

import java.util.ArrayList;

import org.jgrapht.util.MathUtil;

import com.badlogic.gdx.math.MathUtils;
import com.leisure.duncraw.art.chara.Observer;
import com.leisure.duncraw.art.chara.State;
import com.leisure.duncraw.art.chara.states.IdleState;
import com.leisure.duncraw.art.chara.states.TalkState;
import com.leisure.duncraw.data.Conversation;
import com.leisure.duncraw.hud.DialogueHud;
import com.leisure.duncraw.logging.Logger;

public class TalkBehaviour extends Observer {
  private final DialogueHud dialogueHud;
  private final ArrayList<Conversation> conversations = new ArrayList<>();
  private Conversation conversation;
  private boolean buffer = false;
  public TalkBehaviour(DialogueHud dialogueHud, Conversation... conversations) {
    this.dialogueHud = dialogueHud;
    for (Conversation conv : conversations) 
      this.conversations.add(conv);
  }
  public TalkBehaviour(DialogueHud dialogueHud, ArrayList<Conversation> conversations) {
    this.dialogueHud = dialogueHud;
    this.conversations.addAll(conversations);
  }
  @Override
  public void invoke(State state) {
    if (state instanceof TalkState) {
      Logger.log("TalkBehaviour", "invoked");
      if (buffer) {
        buffer = false;
        conversation = conversations.get(MathUtils.random(conversations.size()-1));
        dialogueHud.start(conversation);
      }
      else if (chara.prevState instanceof TalkState && conversations != null) {
        if (!dialogueHud.next()) {
          chara.lockState = false;
          ((TalkState)state).target.lockState = false;
          // For next talk
          buffer = true;
        }
      }
      else {
        conversation = conversations.get(MathUtils.random(conversations.size()-1));
        dialogueHud.start(conversation);
      }
    } 
  } 
  @Override
  public Observer copy() {
    return new TalkBehaviour(dialogueHud, conversation);
  }
}
