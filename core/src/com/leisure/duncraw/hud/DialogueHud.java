package com.leisure.duncraw.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.data.Conversation;

public class DialogueHud extends Hud {
  public Conversation conversation;
  private final BitmapFont font;
  public DialogueHud() {
    font = Graphics.getFont(Graphics.fontSources.def);
    // font.getData().setScale(3);
  }
  @Override
  public boolean canRender() {
    return conversation != null && !conversation.hasEnded();
  }
  public void start(Conversation conversation) {
    this.conversation = conversation;
    conversation.start();
  }
  public boolean next() {
    return conversation.next() != null;
  }
  public void end() {
  }
  @Override
  public void draw(Batch batch, float parentAlpha) {
    // Logger.log("DialogueHud", conversation.currMessage.message);
    font.draw(batch, conversation.currMessage.message, 0, 0);
  }
}
