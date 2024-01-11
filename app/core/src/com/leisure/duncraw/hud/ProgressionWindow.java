package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.leisure.duncraw.data.UiData;

public class ProgressionWindow extends Hud {
  public MapFull map;
  public QuestFull quest;
  public ProgressionWindow(MapFull mapHud, QuestFull quest) {
    this.map = mapHud;
    this.quest = quest;
    add(map).center().expand();
    add(quest).center().top().expand();
    
  }
  @Override
  public Hud init(Stage stage, UiData data, ShapeRenderer renderer, SpriteBatch batch) {
    map.init(stage, data, renderer, batch);
    quest.init(stage, data, renderer, batch);
    return super.init(stage, data, renderer, batch);
  }
  @Override
  public void drawShapes() {
    map.drawShapes();
    quest.drawShapes();
  }
  @Override
  public void update() {
    map.update();
    quest.update();
  }
}
