package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.data.StorySceneData;
import com.leisure.duncraw.manager.StoryManager;

public class QuestFull extends Hud { 
  protected final BitmapFont font;
  private final StoryManager storyManager;
  public QuestFull(StoryManager storyManager) {
    this.storyManager = storyManager;
    font = Graphics.getFont(Graphics.fontSources.normal);
    setVisible(true);  
  }
  @Override
  public void drawShapes() {
    batch.begin();
    for (int  i = 0; i < storyManager.data.scenes.size(); i++) {
      font.draw(batch, storyManager.data.scenes.get(i).title, getGlobalX(), getGlobalY() - (i*32));
    } 
    batch.end();
  }
}
