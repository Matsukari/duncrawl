package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.data.Conversation;

public class DialogueHud extends Hud {
  private ImageTextButton dialogueHandle;
  public Conversation conversation;
  public DialogueHud() {
  }
  @Override
  protected void onInit() {
    BitmapFont font = Graphics.getFont(Graphics.fontSources.def);
    Image background = new Image(Graphics.getTextureRegion(data.dialogueBackground)); 
    background.setScale(2f);
    dialogueHandle = new ImageTextButton("", 
        new ImageTextButton.ImageTextButtonStyle(background.getDrawable(), background.getDrawable(), background.getDrawable(), font));
    // dialogueHandle.setSize(200, 200);
    dialogueHandle.getStyle().fontColor = Color.WHITE;
    dialogueHandle.getStyle().up.setLeftWidth(100);
    dialogueHandle.getStyle().up.setRightWidth(100);
    dialogueHandle.getStyle().up.setMinHeight(100);
    add(dialogueHandle).center().bottom();
    setVisible(false);  
  }
  public void start(Conversation conversation) {
    this.conversation = conversation;
    conversation.start();
    dialogueHandle.setText(conversation.currMessage.message);
    setVisible(true);
    if (conversation.hasEnded()) setVisible(false);
  }
  public void restart() {
    conversation.restart();
  }
  public boolean next() {
    if (conversation.next() == null) {
      setVisible(false);
      return false;
    }
    dialogueHandle.setText(conversation.currMessage.message);
    setVisible(true);
    return true;
  }
  public void end() {
  }
}
