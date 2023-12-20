package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.data.Conversation;

public class DialogueHud extends Hud {
  private BitmapFont font;
  private ImageTextButton dialogueHandle;
  public Conversation conversation;
  public DialogueHud() {
  }
  @Override
  protected void onInit() {
    font = Graphics.getFont(Graphics.fontSources.def);
    font.setColor(Color.RED);
    Image background = new Image(Graphics.getTextureRegion(data.dialogueBackground)); 
    dialogueHandle = new ImageTextButton("", 
        new ImageTextButton.ImageTextButtonStyle(background.getDrawable(), background.getDrawable(), background.getDrawable(), font));
    // dialogueHandle.setSize(200, 200);
    dialogueHandle.getStyle().fontColor = Color.WHITE;
    add(dialogueHandle).center().bottom().expand();
    setVisible(false);  
  }
  public void start(Conversation conversation) {
    this.conversation = conversation;
    conversation.start();
    dialogueHandle.setText(conversation.currMessage.message);
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
