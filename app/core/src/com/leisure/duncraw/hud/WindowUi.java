package com.leisure.duncraw.hud;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentNavigableMap;

import org.lwjgl.opengl.GL20;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.StringBuilder;
import com.leisure.duncraw.logging.Logger;

public class WindowUi extends Hud {
  public ArrayList<Tab> tabs;
  public StatusWindow statusWindow;
  public ProgressionWindow progressionWindow;
  public SettingsWindow settingsWindow;
  public Color containerBgColor = Color.valueOf("#101010");
  public Color panelBgColor = Color.valueOf("#151515");
  public Color containerOutlineBgColor = Color.valueOf("#555555");
  protected Stack tabPanel;
  public Tab currentTab;
  public int currentIndex;
  public WindowUi(StatusFull statusHud, InventoryHud inventoryHud, MapFull mapHud, QuestFull quest) {
    tabs = new ArrayList<>();
    statusWindow = new StatusWindow(statusHud, inventoryHud);
    progressionWindow = new ProgressionWindow(mapHud, quest);
    settingsWindow = new SettingsWindow();
  }
  @Override
  protected void onInit() {
    tabs.add(new Tab(createLabel("Status"), statusWindow));
    tabs.add(new Tab(createLabel("Progression"), progressionWindow));
    tabs.add(new Tab(createLabel("Settings"), settingsWindow));
   
    tabPanel = new Stack();
    Table tabRow = new Table();
    for (Tab tab : tabs) {
      tab.content.init(stage, data, shapeRenderer, batch);
      tabRow.add(tab.title).center().top().expand().fill();
      tabPanel.addActor(tab.content);
      tab.title.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          Logger.log("WindowUi", "Tab click") ;
          switchTab(tab.title.getText());
        }
      });
    }
    add(tabRow).top().expandX().fillX().height(60).padLeft(10);
    row(); add(tabPanel).fill().expand().center();
    center();
    switchTab(0);
    setVisible(false);  
  }
  @Override
  public void drawShapes() {
    if (!isVisible()) return;
    // Gdx.gl.glEnable(GL20.GL_BLEND);
    // Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    shapeRenderer.begin(ShapeType.Filled);
    panelBgColor.a = getAlpha();
    containerBgColor.a = getAlpha();
    containerOutlineBgColor.a = getAlpha();
    shapeRenderer.setColor(containerBgColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY(), getWidth(), getHeight());
    shapeRenderer.setColor(panelBgColor);
    shapeRenderer.rect(getGlobalX() + tabPanel.getX(), getGlobalY() - tabPanel.getY(), tabPanel.getWidth(), tabPanel.getHeight());
    shapeRenderer.end();
    shapeRenderer.begin(ShapeType.Line);
    shapeRenderer.setColor(containerOutlineBgColor);
    shapeRenderer.rect(getGlobalX(), getGlobalY(), getWidth(), getHeight());
    shapeRenderer.end();
    if (currentTab.content.isVisible()) currentTab.content.drawShapes();
  }
  @Override
  public void update() {
    currentTab.content.update();
  }
  public void switchTab(int index) {
    for (Tab tab : tabs) tab.content.setVisible(false);
    currentTab = tabs.get(index);
    currentTab.content.setVisible(true);
    currentIndex = index;
  }
  public void switchTab(StringBuilder title) {
    for (int i = 0; i < tabs.size(); i++) {
      tabs.get(i).content.setVisible(false);
      if (tabs.get(i).title.getText().equalsIgnoreCase(title)) {
        currentTab = tabs.get(i);
        currentIndex = i;
      }
    }
    currentTab.content.setVisible(true);
  }

}
