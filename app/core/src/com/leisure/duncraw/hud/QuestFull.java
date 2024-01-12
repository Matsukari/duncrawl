package com.leisure.duncraw.hud;

import org.locationtech.jts.geom.Coordinate;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.leisure.duncraw.Graphics;
import com.leisure.duncraw.art.chara.Player;
import com.leisure.duncraw.data.StorySceneData;
import com.leisure.duncraw.manager.StoryManager;

public class QuestFull extends Hud { 
  protected final BitmapFont font;
  private final StoryManager storyManager; 
  public float sceneMarginY = 10;
  public float sceneFontScale = 0.97f;
  public Color sceneFontColor = Color.valueOf("#555555");
  public Color sceneFontActiveColor =  Color.valueOf("#aaaaaa");
  public Color scenesContainerColor = Color.valueOf("#1a1a1a");

  public QuestFull(StoryManager storyManager) {
    this.storyManager = storyManager;
    font = Graphics.getFont(Graphics.fontSources.normal);
    setVisible(true);  
  }
  @Override
  public void drawShapes() {
    float stackY = getGlobalY();

    stackY -= 10;

    batch.begin();
    for (int  i = 0; i < storyManager.data.scenes.size(); i++) {
      font.getData().setScale(sceneFontScale);
      if (i >= storyManager.data.scenes.size()-1) font.setColor(sceneFontActiveColor);
      else font.setColor(sceneFontColor);
      font.draw(batch, String.format("%d. %s", i, storyManager.data.scenes.get(i).title), getGlobalX(), stackY - (i*32));
      font.setColor(Color.WHITE);
      font.getData().setScale(1f);
    } 
    batch.end();
  }
}
