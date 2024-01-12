package com.leisure.duncraw.hud;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.leisure.duncraw.data.UiData;

import lib.math.Pointi;

public class ProgressionWindow extends Hud {
  public MapFull map;
  public QuestFull quest;


  public ProgressionWindow(MapFull mapHud, QuestFull quest) {
    this.map = mapHud;
    this.quest = quest;
    add(map).center().left().fill().padLeft(20).width(400);
    add(quest).center().left().top().expand().padRight(20).padTop(20).spaceLeft(50);
    
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
