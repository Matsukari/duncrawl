package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.data.Conversation;

public class DialogueHud extends Hud {
  private final BitmapFont font;
  private final Label.LabelStyle labelStyle;
  private final Label dialogueLabel;
  public Conversation conversation;
  public DialogueHud() {
    font = Graphics.getFont(Graphics.fontSources.def);
    labelStyle = new Label.LabelStyle(font, Color.WHITE);
    dialogueLabel = new Label("", labelStyle);
    add(dialogueLabel).center().bottom().expand();
    setVisible(false);  
  }
  public void start(Conversation conversation) {
    this.conversation = conversation;
    conversation.start();
    dialogueLabel.setText(conversation.currMessage.message);
    setVisible(true);
    if (conversation.hasEnded()) setVisible(false);
  }
  public boolean next() {
    if (conversation.next() == null) {
      setVisible(false);
      return false;
    }
    setVisible(true);
    return true;
  }
  public void end() {
  }
}
